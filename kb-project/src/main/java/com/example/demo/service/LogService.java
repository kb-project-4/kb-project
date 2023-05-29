package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BankAccountDto;
import com.example.demo.dto.TransferDto;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Log;
import com.example.demo.entity.User;
import com.example.demo.repository.LogRepository;

@Service
public class LogService {

	private final LogRepository logRepository;
	private final UserService userService;
//	private final BankAccountService bankAccountService;

	@Autowired
	public LogService(LogRepository logRepository, UserService userService) {
		this.logRepository = logRepository;
		this.userService = userService;
//		this.bankAccountService = bankAccountService;
//		this.bankAccountRepository = bankAccountRepository;
	}

	public void save(Log logentity) {

		logRepository.save(logentity); // 계좌 내역 저장
	}

//	public List<Log> getlogs(User user, String mybanknumber) {
//		List<Log> logs = logRepository.findAll();
//		List<Log> logsList = new ArrayList<Log>();
//		String userid = user.getUserid();
//		for (Log log : logs) { //
//			if (log.getUser().getUserid().equals(userid) && (log.getRecipient_banknumber().equals(mybanknumber)
//					|| log.getSender_banknumber().equals(mybanknumber))) {
//
//				logsList.add(log);
//			}
//
//		}
//
//		return logsList;
//	}

	public List<Log> getlogs(User user, String mybanknumber) {
		List<Log> logs = logRepository.findAll();
		String userid = user.getUserid();

		List<Log> filteredLogs = logs.stream().filter(log -> log.getUser().getUserid().equals(userid))
				.filter(log -> log.getRecipient_banknumber().equals(mybanknumber)
						|| log.getSender_banknumber().equals(mybanknumber))
				.collect(Collectors.toList());

		Collections.sort(filteredLogs, Comparator.comparing(Log::getCreatedDate).reversed());

		return filteredLogs;
	}
	// Add other methods as needed

}