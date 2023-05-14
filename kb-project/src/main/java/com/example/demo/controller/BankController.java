package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Service.BankService;
import com.example.demo.entity.Bank;

@Controller
@RequestMapping("/banks")
public class BankController {

	private final BankService bankService;

	public BankController(BankService bankService) {
		this.bankService = bankService;
	}

	@GetMapping
	public String getAllBanks(Model model) {
		List<Bank> banks = bankService.getAllBanks();
		model.addAttribute("banks", banks);
		return "bank/list";
	}

	@GetMapping("/create")
	public String showCreateBankForm(Model model) {
		model.addAttribute("bank", new Bank());
		return "bank/create";
	}

	@PostMapping("/create")
	public String createBank(@ModelAttribute("bank") Bank bank) {
		bankService.createBank(bank);
		return "redirect:/banks";
	}

	@GetMapping("/{id}/edit")
	public String showEditBankForm(@PathVariable Long id, Model model) {
		Bank bank = bankService.getBankById(id);
		model.addAttribute("bank", bank);
		return "bank/edit";
	}

	@PostMapping("/{id}/edit")
	public String editBank(@PathVariable Long id, @ModelAttribute("bank") Bank bank) {
		bank.setId(id);
		bankService.updateBank(bank);
		return "redirect:/banks";
	}

	@GetMapping("/{id}/delete")
	public String deleteBank(@PathVariable Long id) {
		bankService.deleteBankById(id);
		return "redirect:/banks";
	}

}
