package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KeyWordController {

	@PostMapping("/process")
	public String processKeyword(@RequestParam("keyword") String keyword) {
		System.out.println("입력한 키워드: " + keyword);
		return "redirect:/result";
	}

	
	@GetMapping("/result")
	public String showResultPage() {
		return "result";
	}

}
