package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BankAccountDto;
import com.example.demo.dto.TransferDto;
import com.example.demo.entity.Bank;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Log;
import com.example.demo.entity.User;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.BankRepository;
import com.example.demo.repository.LogRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class BankAccountService {

	private final BankAccountRepository bankAccountRepository;

	private final UserRepository userRepository;
	private final UserService userService;
	private final BankService bankService;
	private final LogService logService;
	private final LogRepository logRepository;

	public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository,
			UserService userService, BankService bankService, LogRepository logRepository, LogService logService) {

		this.bankAccountRepository = bankAccountRepository;
		this.userRepository = userRepository;
		this.userService = userService;
		this.bankService = bankService;
		this.logRepository = logRepository;
		this.logService = logService;
	}

	public BankAccount updateBankAccount(BankAccountDto bankAccount) {
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

	@Transactional
	public void transferToUser(TransferDto log, User me) {

		System.out.println("savelog");
		System.out.println("user" + me.toString()); // 본인

		BankAccount mybankAccount = new BankAccount();

		System.out.println("log.get" + log.getMy_banknumber()); // 내 계좌번호

		mybankAccount = bankAccountRepository.findByAccountNumber(log.getMy_banknumber());

		System.out.println("mybankid1" + mybankAccount.getId());

		System.out.println("mybankaccount" + mybankAccount.toString());
		Long amount = mybankAccount.getAmount();// 본인돈
		System.out.println("amount" + amount);

		Long sendamount = log.getAmount(); // 보낼 돈 액수

		if (amount - sendamount < 0) {
			System.out.println("잔액부족");

		} else {

			System.out.println("remain" + (amount - sendamount));

			mybankAccount.setAmount(amount - sendamount);// 본인계좌

			String name = log.getRecipient_name();
			User user1 = userService.getUserByUsername(name);// 받는사람
			System.out.println("rec name" + name);

			Long recipent_curmoney = 0L;
			BankAccount bankAccount = new BankAccount();
			for (BankAccount bankAccounts1 : user1.getBankAccounts()) {

				if (bankAccounts1.getAccountNumber().equals(log.getRecipient_banknumber())) {
					recipent_curmoney = bankAccounts1.getAmount();// 받는사람현재잔액
					bankAccount = bankAccounts1;// 받는사람 계좌
				}

			}

			bankAccount.setAmount(recipent_curmoney + sendamount); // 받는사람계좌 돈 증가

			System.out.println("rec amountcur" + recipent_curmoney);// 받는사람 현재잔액

//			List<BankAccount> mybankAccountlist = new ArrayList<>(me.getBankAccounts());

//			mybankAccountlist.set(idx, mybankAccount); // 본인계좌 업데이트

			System.out.println("mybaid" + mybankAccount.toDto().getId());

			updateBankAccount(mybankAccount.toDto()); // 내 계좌에 돈 이빠졋으니 업데이트

			Log logentity = log.toEntity();

			logService.update(logentity);

		}

	}
}
