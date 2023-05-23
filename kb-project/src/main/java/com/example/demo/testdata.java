//package com.example.demo;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.demo.entity.User;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//
///**
// * userA - JPA1 BOOK - JPA2 BOOK
// *
// * userB - SPRING1 BOOK - SPRING2 BOOK
// */
//
//@Component
//@RequiredArgsConstructor
//public class testdata {
//
//	private final InitService initService;
//
//	@PostConstruct
//	public void init() {
//		initService.dbInit1();
//	}
//
//	@Component
//	@Transactional
//	@RequiredArgsConstructor
//	static class InitService {
//
//		private final EntityManager em;
//
//		public void dbInit1() {
//			User user = createMember("jamwondong", true, "0000", "010-2111-2222", "won", "junwon");
//			em.persist(user);
//
//		}
//
//		private User createMember(String address, boolean disabled, String password, String phone, String userid,
//				String username) {
//			User us = new User();
//			us.setAddress(address);
//			us.setDisabled(disabled);
//			us.setPassword(password);
//			us.setPhone(phone);
//			us.setUsername(username);
//			us.setUserid(userid);
//
//			return us;
//
//		}
//
//	}
//
//}