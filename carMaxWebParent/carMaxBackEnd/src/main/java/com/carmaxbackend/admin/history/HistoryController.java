package com.carmaxbackend.admin.history;

import com.carmax.common.entity.*;
import com.carmax.common.exception.HistoryNotFoundException;
import com.carmaxbackend.admin.FileUploadUtil;
import com.carmaxbackend.admin.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HistoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HistoryController.class);
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ProductService productService;

	@GetMapping("/history")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "createdTime", "asc", null);
	}

	@GetMapping("/history/page/{pageNum}")
	public String listByPage(
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword
	) {
		Page<History> page = historyService.listByPage(pageNum, sortField, sortDir, keyword);
		List<History> listHistory = page.getContent();

		long startCount = (pageNum - 1) * historyService.HISTORY_PER_PAGE + 1;
		long endCount = startCount + historyService.HISTORY_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("listHistory", listHistory);

		return "history/history";
	}

	@GetMapping("/history/new")
	public String newHistory(Model model) {
		List<Product> listProducts = productService.listAll();

		model.addAttribute("listProducts", listProducts);
		model.addAttribute("history", new History());
		model.addAttribute("pageTitle", "Create New History");
		model.addAttribute("numberOfExistingExtraImages", 0);

		return "history/history_form";
	}

	@PostMapping("/history/save")
	public String saveHistory(History history, @RequestParam("createdTime") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date createdTime,
							  @RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,
							  @RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,
							  @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
							  @RequestParam(name = "imageNames", required = false) String[] imageNames,
							  RedirectAttributes ra) throws IOException {

		history.setCreatedTime(new java.sql.Date(createdTime.getTime()));

		setMainImageName(mainImageMultipart, history);
		setExistingExtraImageNames(imageIDs, imageNames, history);
		setNewExtraImageNames(extraImageMultiparts, history);

		History savedHistory = historyService.save(history);

		saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedHistory);

		deleteExtraImagesWeredRemovedOnForm(history);

		ra.addFlashAttribute("message", "The history has been saved successfully.");
		return "redirect:/history";
	}

	private void saveUploadedImages(MultipartFile mainImageMultipart,
									MultipartFile[] extraImageMultiparts, History savedHistory) throws IOException {
		if (!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			String uploadDir = "../history-images/" + savedHistory.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
		}

		if (extraImageMultiparts.length > 0) {
			String uploadDir = "../history-images/" + savedHistory.getId() + "/extras";

			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (multipartFile.isEmpty()) continue;

				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			}
		}

	}

	static void deleteExtraImagesWeredRemovedOnForm(History history) {
		String extraImageDir = "../history-images/" + history.getId() + "/extras";
		Path dirPath = Paths.get(extraImageDir);

		try {
			Files.list(dirPath).forEach(file -> {
				String filename = file.toFile().getName();

				if (!history.containsImageName(filename)) {
					try {
						Files.delete(file);
						LOGGER.info("Deleted extra image: " + filename);

					} catch (IOException e) {
						LOGGER.error("Could not delete extra image: " + filename);
					}
				}

			});
		} catch (IOException ex) {
			LOGGER.error("Could not list directory: " + dirPath);
		}
	}

	static void setExistingExtraImageNames(String[] imageIDs, String[] imageNames,
										   History history) {
		if (imageIDs == null || imageIDs.length == 0) return;

		Set<HistoryImage> images = new HashSet<>();

		for (int count = 0; count < imageIDs.length; count++) {
			Integer id = Integer.parseInt(imageIDs[count]);
			String name = imageNames[count];

			images.add(new HistoryImage(id, name, history));
		}

		history.setImages(images);

	}

	private void setNewExtraImageNames(MultipartFile[] extraImageMultiparts, History history) {
		if (extraImageMultiparts.length > 0) {
			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (!multipartFile.isEmpty()) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
					if (!history.containsImageName(fileName)) {
						history.addExtraImage(fileName);
					}
				}
			}
		}
	}

	private void setMainImageName(MultipartFile mainImageMultipart, History history) {
		if (!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			history.setMainImage(fileName);
		}
	}

	@GetMapping("/history/{id}/enabled/{status}")
	public String updateHistoryEnabledStatus(@PathVariable("id") Integer id,
											  @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		historyService.updateHistoryEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The History ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/history";
	}

	@GetMapping("/history/delete/{id}")
	public String deleteHistory(@PathVariable(name = "id") Integer id,
								Model model,
								RedirectAttributes redirectAttributes) {
		try {
			historyService.delete(id);
			String historytExtraImagesDir = "../history-images/" + id + "/extras";
			String historyImagesDir = "../history-images/" + id;

			FileUploadUtil.removeDir(historytExtraImagesDir);
			FileUploadUtil.removeDir(historyImagesDir);


			redirectAttributes.addFlashAttribute("message",
					"The history ID " + id + " has been deleted successfully");
		} catch (HistoryNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/history";
	}

	@GetMapping("/history/edit/{id}")
	public String editHistory(@PathVariable("id") Integer id, Model model,
				RedirectAttributes ra) {

		try {
			History history = historyService.get(id);
			List<Product> listProducts = productService.listAll();
			Integer numberOfExistingExtraImages = history.getImages().size();

			model.addAttribute("listProducts", listProducts);
			model.addAttribute("history", history);
			model.addAttribute("pageTitle", "Edit History (ID: " + id + ")");
			model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);

			return "history/history_form";

		} catch (HistoryNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/history";
		}
	}

	@GetMapping("/history/detail/{id}")
	public String viewHistoryDetails(@PathVariable("id") Integer id, Model model,
									 RedirectAttributes ra) {
		try {
			History history = historyService.get(id);
			model.addAttribute("history", history);

			return "history/history_detail_modal";

		} catch (HistoryNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/history";
		}
	}
}
