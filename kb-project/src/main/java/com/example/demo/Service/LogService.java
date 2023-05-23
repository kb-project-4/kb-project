package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LogDto;
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
	public void saveLog(LogDto log, User me) {

//		HttpSession session = request.getSession();
//		User me = (User) session.getAttribute("user");// 본인

		System.out.println("savelog");
		System.out.println("user" + me.toString());
		BankAccount mybankAccounts = new BankAccount();

		int idx = 0;// 본인계좌 인덱스
		int i = 0;
		System.out.println("log.get" + log.getMy_banknumber());

		for (BankAccount bankAccounts1 : me.getBankAccounts()) {
			System.out.println("bankaccount" + bankAccounts1.getAccountNumber());
			if (bankAccounts1.getAccountNumber().equals(log.getMy_banknumber())) {
				System.out.println("succ");
				mybankAccounts = bankAccounts1; // 본인계좌
				idx = i;
			}
			i++;
		}

		System.out.println("mybankaccount" + mybankAccounts.toString());
		Long amount = mybankAccounts.getAmount();// 본인돈
		System.out.println("amount" + amount);

		Long sendamount = log.getAmount(); // 보낼 돈 액수

		if (amount - sendamount < 0) {
			System.out.println("잔액부족");

		} else {

			System.out.println("remain" + (amount - sendamount));

			mybankAccounts.setAmount(amount - sendamount);// 본인계좌

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

			bankAccount.setAmount(recipent_curmoney + sendamount);

			System.out.println("rec amountcur" + recipent_curmoney);// 받는사람 현재잔액

			List<BankAccount> mybankaccountslist = new ArrayList<>(me.getBankAccounts());

			mybankaccountslist.set(idx, mybankAccounts); // 본인계좌 업데이트
//
//			for (BankAccount bankAccount2 : mybankaccountslist) {
//
//				System.out.println("bankaccounts" + bankAccount2.toString());
//			}

			bankAccountService.updateBankAccount(mybankAccounts);

//			me.setBankAccounts(mybankaccountslist); // 유저 계좌에 새계좌 업데이트

//			log.setCategory("송금");

			Log log2 = log.toEntity();

			logRepository.save(log2);

		}

	}

	// Add other methods as needed

}