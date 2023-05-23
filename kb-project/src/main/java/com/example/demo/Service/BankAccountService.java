package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.LogDto;
import com.example.demo.entity.Bank;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.BookMark;
import com.example.demo.entity.User;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class BankAccountService {

	private final BankAccountRepository bankAccountRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final BankService bankService;
	private final LogService logService;
	

	public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository,
			UserService userService, BankService bankService, LogService logService) {
		this.bankAccountRepository = bankAccountRepository;
		this.userRepository = userRepository;
		this.userService = userService;
		this.bankService = bankService;
		this.logService = logService;
	}

	// 계좌 조회
	public BankAccount updateBankAccount(BankAccount bankAccount) {
		BankAccount existingAccount = bankAccountRepository.findById(bankAccount.getId()).orElse(null);

		if (existingAccount != null) {
			// 기존 계좌 정보 업데이트
			existingAccount.setAccountNumber(bankAccount.getAccountNumber());
			existingAccount.setAmount(bankAccount.getAmount());
			existingAccount.setBank(bankAccount.getBank());
			existingAccount.setUser(bankAccount.getUser());

			// 변경사항 저장
			return bankAccountRepository.save(existingAccount);
		}

		return null; // 계좌가 존재하지 않을 경우 null 반환
	}

	public List<BankAccount> getBankAccountByuserId(User user) { // User 계좌 리스트
		Long userId = user.getId();
		List<BankAccount> userBankAccounts = bankAccountRepository.findByAllUserId(userId);
		System.out.println(userBankAccounts);
		return userBankAccounts;
	}
	
	public BankAccount createBankAccount(BankAccount bankAccount, Bank bank, User user) {
		bankAccount.setUser(user);
		bankAccount.setBank(bank);
		return bankAccountRepository.save(bankAccount);
	}

	@Transactional
	public BankAccount deleteBankAccount(BankAccount bankAccount) {
		String accountnumber = bankAccount.getAccountNumber();
		bankAccountRepository.deleteByAccountNumber(accountnumber);

		return bankAccount;
	}

	// BookMark User에게 송금
	public void transferToBookMarkUser(BookMark recepient, User user, Long amount) {
		
		
	}

}
