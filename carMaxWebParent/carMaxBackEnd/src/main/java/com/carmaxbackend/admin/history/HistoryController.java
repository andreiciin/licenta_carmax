package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import com.carmax.common.entity.Product;
import com.carmax.common.exception.HistoryNotFoundException;
import com.carmaxbackend.admin.FileUploadUtil;
import com.carmaxbackend.admin.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.sql.Date;
import java.util.List;

@Controller
public class HistoryController {

	@Autowired
	private HistoryService historyService;
	@Autowired
	private ProductService productService;

	@GetMapping("/history")
	public String listAll(Model model) {
		List<History> listHistory = historyService.listAll();

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
							  RedirectAttributes ra) throws IOException {

		history.setCreatedTime(new java.sql.Date(createdTime.getTime()));

		setMainImageName(mainImageMultipart, history);
		setExtraImageNames(extraImageMultiparts, history);

		History savedHistory = historyService.save(history);

		saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedHistory);

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

	private void setExtraImageNames(MultipartFile[] extraImageMultiparts, History history) {
		if (extraImageMultiparts.length > 0) {
			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (!multipartFile.isEmpty()) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
					history.addExtraImage(fileName);
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
}
