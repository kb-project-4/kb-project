package com.example.demo.config;

import org.springframework.stereotype.Component;

@Component
public class YourBean {
	private String keyword;

	public YourBean(String keyword) {
		this.keyword = keyword;
	}

	public void executeLogic() {
		System.out.println("Executing logic for keyword: " + keyword);
		// 실행 로직을 작성합니다.
	}
	
}
