package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

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
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.LogRepository;

@Service
public class LogService {

	private final LogRepository logRepository;
//	private final BankAccountRepository bankAccountRepository;
	private final UserService userService;

	@Autowired
	public LogService(LogRepository logRepository, UserService userService) {
		this.logRepository = logRepository;
		this.userService = userService;
//		this.bankAccountRepository = bankAccountRepository;
	}

	
	public void update(Log logentity) {

		logRepository.save(logentity); // 계좌 내역 저장
	}

	// Add other methods as needed

}