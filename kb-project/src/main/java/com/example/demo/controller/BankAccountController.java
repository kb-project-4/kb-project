package com.example.demo.controller;

import com.example.demo.Service.BankAccountService;
import com.example.demo.entity.Bank;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;
import com.example.demo.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BankAccountController {

	private final BankAccountService bankAccountService;

	@Autowired
	public BankAccountController(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	@GetMapping("/bankaccounts")
	public String getBankAccounts(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userid = user.getUserid();

		List<BankAccount> bankAccounts = bankAccountService.getBankAccountByuserId(request);
		model.addAttribute("bankAccounts", bankAccounts);
		return "bankaccount/list";
	}

	@PostMapping("/bankaccounts")
	public String createBankAccount(@ModelAttribute("bankAccount") BankAccount bankAccount,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		bankAccount.setUser(user);
		Bank bank = null;
		
		bankAccountService.createBankAccount(bankAccount, user, bank);

		return "redirect:/bankaccounts";
	}

	// 다른 HTTP 요청에 대한 메서드 작성 (계좌 생성, 수정, 삭제 등)
}
