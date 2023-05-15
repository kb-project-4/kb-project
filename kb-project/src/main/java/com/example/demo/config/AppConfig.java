package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean("송금")
	public String yourBean1() {
		return "hello";
	}

	
//
//	@Bean("keyword2")
//	public YourBean yourBean2() {
//		return new YourBean("Keyword 2");
//	}

	// 다른 빈들을 추가로 등록할 수 있습니다.
}
