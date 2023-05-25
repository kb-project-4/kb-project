package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.TransferDto;
import com.example.demo.dto.Login;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Bank;
import com.example.demo.entity.Log;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BankAccountService;
import com.example.demo.service.LogService;
import com.example.demo.service.UserService;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	@Autowired
	private BankAccountService bankaccountservice;

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
	public String createUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
		if (result.hasErrors()) {
			return "user/new";
		}
		System.out.println(user.getAddress());
		System.out.println(user.getPassword());
//
//		User defaultuser = new User();
//		defaultuser.setAddress("잠원");
//		defaultuser.setAddress("잠원");

		userService.createUser(user);
		return "redirect:/users/index";

	}

	@GetMapping("/users/edit/{id}")
	public String editUserForm(@PathVariable("id") Long id, Model model) {

		Optional<User> user = userService.getUserById(id);
//		User user = userService.getUserById(id).get();

		model.addAttribute("user", user.get());
//		model.addAttribute("user.id", id);
		model.addAttribute("user.id", user.get().getId());
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
	public String mainpage(@ModelAttribute("login") Login login, RedirectAttributes redirectAttributes,
			HttpSession session) {

		User userByUserId = userService.getUserByUserId(login.getUserid());

		if (userByUserId != null && login.getPassword().equals(userByUserId.getPassword())) {
			session.setAttribute("user", userByUserId); // 세션에 사용자 정보 저장

			if (userByUserId.isDisabled() == false) {

				return "redirect:/users/main";

			}

			else {
				// 장애인이동
				System.out.println("장애인");
				return "redirect:/users/main";

			}
		}

		else {
			redirectAttributes.addFlashAttribute("errorMessage", "회원정보 오류");

			return "redirect:/users/index";
		}

	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		// 세션 무효화
		HttpSession session = request.getSession();
		if (session != null) {
			System.out.println("session inval");
			session.invalidate();
		}
		// 로그인 페이지로 리다이렉트
		return "redirect:/users/index";
	}

	@GetMapping("users/main")
	public String main(HttpServletRequest request, Model model) {
		// 여기에서 필요한 데이터를 모델에 추가하면 됩니다.

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userid = user.getUserid();

		model.addAttribute("userid", userid);

		return "user/main";

	}

	@GetMapping("/transfer")
	public String transferform(HttpSession session, HttpServletRequest request, Model model) {

		session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userid = user.getUserid();
		TransferDto transferDto = new TransferDto();
		transferDto.setSender(user);
//		System.out.println("log users" + transferDto.getSender().getBankAccounts());
		model.addAttribute("Log", transferDto);
		return "user/transfer";

	}

	@PostMapping("/transfer")
	public String transfer(HttpSession session, @ModelAttribute("Log") TransferDto log) {

		User user = (User) session.getAttribute("user");
		String userid = user.getUserid();

		log.setSender(user);
		log.setCategory("transfer");
		System.out.println(log);
		System.out.println("user" + log.getSender().getUsername());
		System.out.println("flag");

		bankaccountservice.transferToUser(log, user);

		System.out.println("userid" + userid);

		return "redirect:/users/main";

	}

}
