package com.example.demo.controller;

import com.example.demo.entity.BankAccount;
import com.example.demo.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BankAccountController {

	private final BankAccountRepository bankAccountRepository;

	@Autowired
	public BankAccountController(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository = bankAccountRepository;
	}

	@GetMapping("/bankaccounts")
	public String getBankAccounts(Model model) {
		List<BankAccount> bankAccounts = bankAccountRepository.findAll();
		model.addAttribute("bankAccounts", bankAccounts);
		return "bankaccount-list";
	}

	// 다른 HTTP 요청에 대한 메서드 작성 (계좌 생성, 수정, 삭제 등)
}
