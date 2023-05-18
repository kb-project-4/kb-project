package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Log;
import com.example.demo.entity.User;
import com.example.demo.repository.LogRepository;

@Service
public class LogService {

	private final LogRepository logRepository;
	private final UserService userService;
	private final BankAccountService bankAccountService;

	@Autowired
	public LogService(LogRepository logRepository, UserService userService, BankAccountService bankAccountService) {
		this.logRepository = logRepository;
		this.userService = userService;
		this.bankAccountService = bankAccountService;
	}

	@Transactional
	public void saveLog(Log log, User user, HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user2 = (User) session.getAttribute("user");

		System.out.println("savelog");
		System.out.println("user" + user2.toString());
		BankAccount bankAccounts = user2.getBankAccounts().get(0);

		Long amount = ((BankAccount) bankAccounts).getAmount();// 본인돈
		System.out.println("amount" + amount);

		Long sendamount = log.getAmount(); // 보낼 돈 액수

		if (amount - sendamount < 0) {
			System.out.println("잔액부족");

		} else {
//			bankAccounts.setAmount(amount - sendamount);

			System.out.println("remain" + (amount - sendamount));

			BankAccount bankAccount = bankAccountService.getBankAccountByuserId(request).get(0);
			
			BankAccount bankAccounts1 = user2.getBankAccounts().get(0);
			bankAccounts1.setAmount(amount - sendamount);

//			bankAccounts1.setAmount(amount);

			String name = log.getRecipient_name();
			User user1 = userService.getUserByUsername(name);// 받는사람
			System.out.println("rec name" + name);

			Long amount1 = user1.getBankAccounts().get(0).getAmount();// 받는사람현재잔액
			user1.getBankAccounts().get(0).setAmount(amount1 + amount);
			System.out.println("rec amountcur" + amount1);

			List<BankAccount> bankAccounts2 = new ArrayList<BankAccount>();
			bankAccounts2.add(bankAccounts1);
			System.out.println("bankaccount number" + bankAccounts1.getAccountNumber());
			user2.setBankAccounts(bankAccounts2);

			logRepository.save(log);

		}

	}

	// Add other methods as needed

}