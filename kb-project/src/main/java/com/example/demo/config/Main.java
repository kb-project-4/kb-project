package com.example.demo.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// ApplicationContext를 생성하고 AppConfig를 설정 클래스로 등록합니다.
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// 키워드 입력
		Scanner scanner = new Scanner(System.in);
		System.out.print("키워드를 입력하세요: ");
		String keyword = scanner.nextLine();
		scanner.close();

		// 키워드로 빈 검색 및 실행
		if (context.containsBean(keyword)) {
			YourBean bean = context.getBean(keyword, YourBean.class);
			bean.executeLogic();
		} else {
			System.out.println("해당하는 키워드의 빈이 존재하지 않습니다.");
		}
		
		
	}
}
