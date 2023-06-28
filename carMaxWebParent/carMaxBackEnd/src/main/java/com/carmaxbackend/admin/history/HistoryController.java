package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import com.carmax.common.entity.Product;
import com.carmaxbackend.admin.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

		return "history/history_form";
	}

	@PostMapping("/history/save")
	public String saveHistory(History history, @RequestParam("createdTime") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date createdTime, RedirectAttributes ra) {
		history.setCreatedTime(new java.sql.Date(createdTime.getTime()));
		historyService.save(history);
		ra.addFlashAttribute("message", "The history has been saved successfully.");
		return "redirect:/history";
	}
}
