package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Bank;
import com.example.demo.repository.BankRepository;

@Service
public class BankService {

	private final BankRepository bankRepository;

	public BankService(BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	public List<Bank> getAllBanks() {
		return bankRepository.findAll();
	}

	public Bank getBankById(Long id) {
		return bankRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bank id: " + id));
	}

	public Bank getBankBybankname(String bankname) {
		return bankRepository.findByBankname(bankname);
	}

	public Bank createBank(Bank bank) {
		return bankRepository.save(bank);
	}

	public Bank updateBank(Long id, Bank bank) {
		return bankRepository.save(bank);
	}

	public void deleteBankById(Long id) {
		bankRepository.deleteById(id);
	}
}
