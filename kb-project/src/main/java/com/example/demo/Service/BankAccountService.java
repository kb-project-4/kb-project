package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Bank;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.BankRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BankAccountService {

	private final BankAccountRepository bankAccountRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final BankService bankService;

	public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository,
			UserService userService, BankService bankService) {
		this.bankAccountRepository = bankAccountRepository;
		this.userRepository = userRepository;
		this.userService = userService;
		this.bankService = bankService;
	}

	public List<BankAccount> getBankAccountByuserId(User user) {
		String userid = user.getUserid();

		List<BankAccount> bankAccounts = bankAccountRepository.findAll();
		List<BankAccount> bankAccounts2 = new ArrayList<BankAccount>();

		if (bankAccounts.isEmpty()) {
			return null;
		} else {

			for (BankAccount bankAccount : bankAccounts) {
				if (bankAccount.getUser().getUserid().equals(userid)) {
					bankAccounts2.add(bankAccount);
				}
			}

			return bankAccounts2;
		}
	}

	// 
	public BankAccount createBankAccount(BankAccount bankAccount, String bankname, String userid) {
		User user = userService.getUserByUserId(userid);
		Bank bank = bankService.getBankBybankname(bankname);
		bankAccount.setUser(user);
		bankAccount.setBank(bank);
		return bankAccountRepository.save(bankAccount);
	}

	
	
}
