package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Bank;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.BankRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
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

	public BankAccount updateBankAccount(BankAccount bankAccount) {
		// 계좌 조회
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
	public BankAccount createBankAccount(BankAccount bankAccount, Bank bank, User user) {

		bankAccount.setUser(user);
		bankAccount.setBank(bank);
		return bankAccountRepository.save(bankAccount);
	}

	//
	
	@Transactional
	public BankAccount deleteBankAccount(BankAccount bankAccount) {
		Long bankaccountid = bankAccount.getId();
		System.out.println("bankaccountid" + bankaccountid);
		String accountnumber = bankAccount.getAccountNumber();
		System.out.println("accountnum" + accountnumber);
		bankAccountRepository.deleteByAccountNumber(accountnumber);

		return bankAccount;
	}
	
	

}
