package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.News;
import com.example.demo.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.TransferDto;
import com.example.demo.dto.Login;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.BankAccountService;
import com.example.demo.service.LogService;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NewsController {

	@Autowired
	private LogService logService;

	@Autowired
	private NewsService newsService;

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@GetMapping("/news")
	public String getNews(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		newsService.savenews();
		List<News> news = newsService.getallnews();

		model.addAttribute("$newsList", news);

		if (user.isDisabled()) {// 장애인

			return "news/list";

		} else {// 비장애인
			return "news/list";
		}

	}

}
