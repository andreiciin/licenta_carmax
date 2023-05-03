package com.carmaxbackend.admin.history;

import com.carmax.common.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HistoryController {

	@Autowired
	private HistoryService historyService;

	@GetMapping("/history")
	public String listAll(Model model) {
		List<History> listHistory = historyService.listAll();

		model.addAttribute("listHistory", listHistory);

		return "history/history";
	}
}
