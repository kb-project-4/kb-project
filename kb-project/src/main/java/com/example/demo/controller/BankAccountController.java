package com.example.demo.controller;

import com.example.demo.Service.BankAccountService;
import com.example.demo.Service.BankService;
import com.example.demo.Service.UserService;
import com.example.demo.entity.Bank;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;
import com.example.demo.repository.BankAccountRepository;

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

@Controller
public class BankAccountController {

	private final BankAccountService bankAccountService;
	private final BankService bankService;
	private final UserService userService;

	public BankAccountController(BankAccountService bankAccountService, BankService bankService,
			UserService userService) {
		this.bankAccountService = bankAccountService;
		this.bankService = bankService;
		this.userService = userService;
	}

	@GetMapping("/bankaccounts") // User Bank Account List
	public String getBankAccounts(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<BankAccount> bankAccounts = bankAccountService.getBankAccountByuserId(user);

		model.addAttribute("bankAccounts", bankAccounts);
		return "bankaccount/list";

	}

	// User Bank Account Create
	// User Clients send Data for Bank Accounts
	@PostMapping("/bankaccounts")
	public String createBankAccount(@ModelAttribute("bankAccount") BankAccount bankAccount,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userid = user.getUserid();
		String bankname = bankAccount.getBank().getBankname();
		Bank bank = bankService.getBankBybankname(bankname);

		bankAccountService.createBankAccount(bankAccount, bank, user);

		return "redirect:/bankaccounts";

	}

	// User Bank Account Create FormPage
	@GetMapping("/bankaccounts/create")
	public String createBankAccountform(Model model) {
		model.addAttribute("bankAccount", new BankAccount());
		return "bankaccount/create";

	}

	@PostMapping("/bankaccounts/create")
	public String createBankAccount(HttpServletRequest request,
			@ModelAttribute("bankaccount") BankAccount bankAccount) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String userid = user.getUserid();
		String bankname = bankAccount.getBank().getBankname();
		Bank bank = bankService.getBankBybankname(bankname);

		bankAccountService.createBankAccount(bankAccount, bank, user);
		return "redirect:/bankaccounts";

	}

	@GetMapping("/bankaccounts/delete") // 삭제
	public String deleteBankAccount(HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String userid = user.getUserid();
		BankAccount bankAccount = bankAccountService.getBankAccountByuserId(user).get(0);
		Long bankid = bankAccount.getBank().getId();
		Bank bankById = bankService.getBankById(bankid);
		System.out.println(bankAccount.getId());
		bankAccountService.deleteBankAccount(bankAccount);
		System.out.println("delete");
		
		return "redirect:/bankaccounts";

	}

	// 다른 HTTP 요청에 대한 메서드 작성 (계좌 생성, 수정, 삭제 등)
}
