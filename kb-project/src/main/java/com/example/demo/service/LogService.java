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
		// 모든 로그를 가져옵니다.
		List<Log> logs = logRepository.findAll();
		String userid = user.getUserid();

		// 조건에 맞는 로그를 필터링합니다.
		List<Log> filteredLogs = logs.stream().filter(log -> log.getUser().getUserid().equals(userid)) // 사용자 아이디가 일치하는
																										// 경우
				.filter(log -> log.getRecipient_banknumber().equals(mybanknumber)
						|| log.getSender_banknumber().equals(mybanknumber)) // 계좌번호가 일치하는 경우
				.collect(Collectors.toList());

		// createdDate 필드를 기준으로 내림차순으로 정렬합니다.
		Collections.sort(filteredLogs, Comparator.comparing(Log::getCreatedDate).reversed());

		// 정렬된 로그 리스트를 반환합니다.
		return filteredLogs;
	}

	// Add other methods as needed

}