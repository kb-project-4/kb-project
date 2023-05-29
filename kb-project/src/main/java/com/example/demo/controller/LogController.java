package com.example.demo.controller;

import com.example.demo.dto.BankAccountDto;
import com.example.demo.entity.Bank;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Log;
import com.example.demo.entity.User;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.service.BankAccountService;
import com.example.demo.service.BankService;
import com.example.demo.service.LogService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;

@Controller
public class LogController {

	private final BankAccountService bankAccountService;
	private final BankService bankService;
	private final UserService userService;
	private final LogService logService;

	public LogController(BankAccountService bankAccountService, BankService bankService, UserService userService,
			LogService logService) {
		this.bankAccountService = bankAccountService;
		this.bankService = bankService;
		this.userService = userService;
		this.logService = logService;
	}

	@GetMapping("/log/{myaccountnumber}") //
	public String getlogs(@PathVariable("myaccountnumber") String myaccountnumber, Model model,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		List<Log> logs = logService.getlogs(user, myaccountnumber);
		

		System.out.println("myaccountnumber" + myaccountnumber);
		System.out.println("logs" + logs.toString());
		model.addAttribute("Log", logs);

		if (user.isDisabled()) {// 장애인

			return "log/logs2";

		} else {// 비장애인
			return "log/logs";
		}

	}

	// 다른 HTTP 요청에 대한 메서드 작성 (계좌 생성, 수정, 삭제 등)
}
