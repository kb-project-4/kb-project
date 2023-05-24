package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BankAccountDto;
import com.example.demo.dto.TransferDto;
import com.example.demo.entity.Bank;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.BookMark;
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
	// 음성인식 + 즐겨찾기 사용자에게 송금
	public void transferToBookMarkUser(BookMark bookMark, User sender, Long amount) {
		// transferDto
//		private User sender;
//		private String recipient_name;
//		private String recipient_banknumber;
//		private String category;
//		private String sender_banknumber;
		
		String recipient_name = bookMark.getBookMarkName();
		String recipient_banknumber = bookMark.getBookMarkAccountNumber();
		String category = "transfer";
		TransferDto transferDto = new TransferDto(sender, recipient_name, recipient_banknumber, category, recipient_banknumber, amount);
		
		transferToUser(transferDto, sender);
	}

	// 송금
	@Transactional
	public void transferToUser(TransferDto transferDto, User sender) {
		// TransferDto
		System.out.println(transferDto.toString());
		BankAccount mybankAccount = new BankAccount();

		mybankAccount = bankAccountRepository.findByAccountNumber(transferDto.getSender_banknumber());
		System.out.println(mybankAccount.toString());
		Long amount = mybankAccount.getAmount();// 본인돈

		Long sendamount = transferDto.getAmount(); // 보낼 돈 액수

		if (amount - sendamount < 0) {
			System.out.println("잔액부족");
		} else {

			mybankAccount.setAmount(amount - sendamount);// 본인계좌

			String recepientName = transferDto.getRecipient_name();
			User user1 = userService.getUserByUsername(recepientName);// 받는사람

			Long recipent_curmoney = 0L;

			BankAccount bankAccount = new BankAccount();
			for (BankAccount bankAccounts1 : user1.getBankAccounts()) {
				if (bankAccounts1.getAccountNumber().equals(transferDto.getRecipient_banknumber())) {
					recipent_curmoney = bankAccounts1.getAmount();// 받는사람현재잔액
					bankAccount = bankAccounts1;// 받는사람 계좌
				}
			}

			bankAccount.setAmount(recipent_curmoney + sendamount); // 받는사람계좌 돈 증가
			updateBankAccount(mybankAccount.toDto()); // 내 계좌에 돈 이빠졋으니 업데이트
			Log logentity = transferDto.toEntity();
			logService.update(logentity);
		}

	}
}
