package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Bank;
import com.example.demo.service.BankService;

@RestController
@RequestMapping("/banks")
public class BankController {

	private final BankService bankService;

	public BankController(BankService bankService) {
		this.bankService = bankService;
	}

	@PostMapping
	public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
		Bank createdBank = bankService.createBank(bank);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBank);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Bank> getBank(@PathVariable Long id) {
		Bank bank = bankService.getBankById(id);
		if (bank != null) {
			return ResponseEntity.ok(bank);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity<List<Bank>> getAllBanks() {
		List<Bank> banks = bankService.getAllBanks();
		return ResponseEntity.ok(banks);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bank) {
		Bank updatedBank = bankService.updateBank(id, bank);
		if (updatedBank != null) {
			return ResponseEntity.ok(updatedBank);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public void deleteBank(@PathVariable Long id) {
		bankService.deleteBankById(id);

	}
}
