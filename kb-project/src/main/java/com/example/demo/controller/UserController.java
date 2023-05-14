package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.UserService;
import com.example.demo.dto.Login;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public String getUsers(Model model) {
		List<User> userList = userService.getAllUsers();
		model.addAttribute("users", userList);
		return "user/list";
	}

	@GetMapping("/users/{id}")
	public String getUser(@PathVariable("id") Long id, Model model) {
		Optional<User> user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "user/view";
	}

	@GetMapping("/users/new")
	public String createUserForm(Model model) {
		model.addAttribute("user", new User());
		return "user/new";
	}

	@PostMapping("/users/new")
	public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
		if (result.hasErrors()) {
			return "user/new";
		}

		userService.createUser(user);
		return "user/main";
		
	}

	@GetMapping("/users/edit/{id}")
	public String editUserForm(@PathVariable("id") Long id, Model model) {

		Optional<User> user = userService.getUserById(id);
//		User user = userService.getUserById(id).get();

		model.addAttribute("user", user.get());
//		model.addAttribute("user.id", id);
		model.addAttribute("user.id", user.get().getId());
		System.out.println("sssss" + user.toString());
		return "user/edit";

	}

	@PostMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") User user,
			BindingResult result) {

		if (result.hasErrors()) {
			return "user/edit";
		}

		System.out.println("ddddd" + id);
		userService.updateUser(id, user);
		return "redirect:/users";

	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/users";
	}

	@GetMapping("/users/index")
	public String mainpageForm(Model model) {
		model.addAttribute("login", new Login());
		return "user/index";
	}

	@PostMapping("/users/index")
	public String mainpage(@ModelAttribute("login") Login login, RedirectAttributes redirectAttributes) {

		System.out.println("idssss: " + login.getUserid());
		System.out.println("idssss: " + login.getPassword());

		User userByUserId = userService.getUserByUserId(login.getUserid());

		System.out.println("ssss" + userByUserId.toString());

		if (userByUserId != null && login.getPassword().equals(userByUserId.getPassword())) {
			System.out.println("success");
			return "user/main";
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "회원정보 오류");

			return "redirect:/users/index";
		}

	}

}
