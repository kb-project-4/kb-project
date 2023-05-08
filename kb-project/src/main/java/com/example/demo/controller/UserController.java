package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Service.UserService;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

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
		return "redirect:/users";
	}

//	@GetMapping("/users/{id}/edit")
	@GetMapping("/users/edit/{id}")
//	@GetMapping("/users/edit")
	public String editUserForm(@PathVariable("id") Long id, Model model) {

		Optional<User> user = userService.getUserById(id);
		model.addAttribute("user", user);

		System.out.println("sssss" + id);
		return "user/edit";

	}

//	@PostMapping("/users/{id}/edit")
	@PostMapping("/users/edit")
//	public String editUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDto userDto,
	public String editUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result) {
		if (result.hasErrors()) {
			return "user/edit";
		}
		
		Long id = userDto.getId();
		
		System.out.println("ddddd" + id);
		userService.updateUser(id, userDto);
		return "redirect:/users";
	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/users";
	}

}
