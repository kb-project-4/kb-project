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
	public void saveLog(LogDto log, User me) { // 송금
		BankAccount mybankAccounts = new BankAccount();

		int idx = 0;// 본인계좌 인덱스
		int i = 0;

		for (BankAccount bankAccounts1 : me.getBankAccounts()) {
			if (bankAccounts1.getAccountNumber().equals(log.getMy_banknumber())) {
				mybankAccounts = bankAccounts1; // 본인계좌
				idx = i;
			}
			i++;
		}

		Long amount = mybankAccounts.getAmount();// 본인돈
		Long sendamount = log.getAmount(); // 보낼 돈 액수

		if (amount - sendamount < 0) {
			System.out.println("잔액부족");

		} else {
			mybankAccounts.setAmount(amount - sendamount);// 본인계좌

			String name = log.getRecipient_name();
			User recepient = userService.getUserByUsername(name);// 받는사람

			Long recipent_curmoney = 0L; 
			BankAccount bankAccount = new BankAccount();
			for (BankAccount bankAccounts1 : recepient.getBankAccounts()) {

				if (bankAccounts1.getAccountNumber().equals(log.getRecipient_banknumber())) {
					recipent_curmoney = bankAccounts1.getAmount();// 받는사람현재잔액
					bankAccount = bankAccounts1;// 받는사람 계좌
				}

			}

			bankAccount.setAmount(recipent_curmoney + sendamount); // 받는사람계좌 돈 증가

			System.out.println("rec amountcur" + recipent_curmoney);// 받는사람 현재잔액

			List<BankAccount> mybankaccountslist = new ArrayList<>(me.getBankAccounts());

			mybankaccountslist.set(idx, mybankAccounts); // 본인계좌 업데이트
// 

			bankAccountService.updateBankAccount(mybankAccounts); //내 계좌에 돈 이빠졋으니 업데이트 

			Log log2 = log.toEntity();

			logRepository.save(log2); //계좌 내역 저장

		}

	}
	


	// Add other methods as needed

}