package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.demo.entity.BankAccount;
import com.example.demo.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.TransferDto;
import com.example.demo.dto.Login;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
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

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@GetMapping("/users")
	public String getUsers(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		List<User> userList = userService.getAllUsers();
		model.addAttribute("users", userList);

		if (user.isDisabled()) {// 장애인

			return "user/list2";

		} else {// 비장애인
			return "user/list";

		}

	}

	@GetMapping("/users/{id}")
	public String getUser(@PathVariable("id") Long id, Model model) {

		Optional<User> user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "user/view";
	}

	@GetMapping("/users/new")
	public String createUserForm(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		model.addAttribute("user", new User());

		if (user.isDisabled()) {// 장애인

			return "user/new2";

		} else {// 비장애인
			return "user/new";

		}

	}

	@PostMapping("/users/new")
	public String createUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "user/new";
		}
		String clientIp = userService.getClientIp(request);
		System.out.println("user Client ip : " + clientIp);
		userDto.setClientSafeIp(clientIp);
		userService.createUser(userDto);
		return "redirect:/users/index";

	}

	@GetMapping("/users/edit/{id}")
	public String editUserForm(@PathVariable("id") Long id, Model model) {

		Optional<User> user = userService.getUserById(id);

		model.addAttribute("user", user.get());
		model.addAttribute("user.id", user.get().getId());

		if (user.get().isDisabled()) {// 장애인

			return "user/edit2";

		} else {// 비장애인
			return "user/edit";

		}

	}

	@PostMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") User user,
			BindingResult result) {

		if (result.hasErrors()) {

			if (user.isDisabled()) {// 장애인

				return "user/edit2";

			} else {// 비장애인
				return "user/edit";

			}

		}

		userService.updateUser(id, user);
		return "redirect:/users";

	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/users";
	}

	@GetMapping("/users/index")
	public String mainpageForm(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		model.addAttribute("login", new Login());
		model.addAttribute("user", new UserDto());

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
			} else {
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

		System.out.println("usercontroller" + user);
		String userid = user.getUserid();

		model.addAttribute("userid", userid);

		if (user.isDisabled()) {// 장애인

			return "user/main2";

		} else {// 비장애인
			return "user/main";

		}

	}

	@GetMapping("/transfer")
	public String transferform(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Long id = user.getId();// 세션갱신이 안되기 땜에 갱신하기 위해서 유저 아이디를 받아오고 밑에 유저값을 다시 받아온다.
		Optional<User> user2 = userService.getUserById(id);
		Long userId = user.getId();
		System.out.println("유저 아이디" + userId);
		TransferDto transferDto = new TransferDto();
		transferDto.setSender(user2.get());
		List<BankAccount> bankAccounts = bankAccountRepository.findAllByUserId(userId);
		System.out.println(bankAccounts);

		model.addAttribute("Log", transferDto);
		model.addAttribute("bankAccounts", bankAccounts);
		if (user.isDisabled()) {// 장애인

			return "user/transfer2";

		} else {// 비장애인
			return "user/transfer";

		}
	}

	@PostMapping("/transfer")
	public String transfer(HttpSession session, @ModelAttribute("Log") TransferDto log, BindingResult result,
			RedirectAttributes redirectAttributes) {

		User user = (User) session.getAttribute("user");
		String userid = user.getUserid();

		log.setSender(user);
		log.setCategory("송금");
		System.out.println(log);
		System.out.println("user" + log.getSender().getUsername());
		System.out.println("flag");

		String account_password = log.getAccount_password();

		boolean iscorrect = verifypassword(user, account_password);

		if (!iscorrect) {
			result.rejectValue("account_password", "password.mismatch", "비밀번호가 틀렸습니다.");
			redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 오류");

			System.out.println("계좌비밀번호가 일치하지않음.");
			return "redirect:/transfer";
		}

		// if (account_password.equals(user.getAccount_password())) {
//
//			bankaccountservice.transferToUser(log, user);
//			return "redirect:/users/main";
//		}

		else {
			System.out.println("계좌비밀번호가 일치.");
			bankaccountservice.transferToUser(log, user);
			return "redirect:/users/main";
		}
	}

	static boolean verifypassword(User user, String account_password) {

		if (account_password.equals(user.getAccount_password())) {

			return true;
		}

		return false;
	}

}
