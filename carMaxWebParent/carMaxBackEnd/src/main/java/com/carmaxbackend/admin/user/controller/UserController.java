package com.carmaxbackend.admin.user.controller;

import com.carmax.common.entity.Role;
import com.carmax.common.entity.User;
import com.carmaxbackend.admin.user.UserNotFoundException;
import com.carmaxbackend.admin.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/users")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "firstName", "asc", null);
	}

	@GetMapping("/users/page/{pageNum}")
	public String listByPage(
							 @PathVariable(name = "pageNum") int pageNum, Model model,
							 @Param("sortField") String sortField,
							 @Param("sortDir") String sortDir,
							 @Param("keyword") String keyword
							 ) {
		System.out.println("Sort field: " + sortField);
		System.out.println("Sort order: " + sortDir);

		Page<User> page = service.listByPage(pageNum, sortField, sortDir, keyword);
		List<User> listUsers = page.getContent();

		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("moduleUrl", "/users");

		return "users/users";
	}

	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();

		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");

		return "users/user_form";
	}

	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		System.out.println(user);
		service.save(user);

		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully!");

		return getRedirectURLtoAffectedUser(user);
	}

	private static String getRedirectURLtoAffectedUser(User user) {
		String firstPartOfEmail = user.getEmail().split("@")[0];
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id,
						   Model model,
						   RedirectAttributes redirectAttributes) {
		try {
			User user = service.get(id);
			List<Role> listRoles = service.listRoles();
			model.addAttribute("user", user);
			model.addAttribute("listRoles", listRoles);
			model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");

			return "users/user_form";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/users";
		}
	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id,
						   Model model,
						   RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The user ID " + id +
					" has been deleted successfully");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/users";
	}

	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id,
										  @PathVariable("status") boolean enabled,
										  RedirectAttributes redirectAttributes) {
		service.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/users";
	}
}
