package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Component
@RequiredArgsConstructor
public class testdata {// db 에자동으로 테스트 데이터 넣어준다.

	private final InitService initService;

	@PostConstruct
	public void init() {
		initService.dbInit1();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {

		private final EntityManager em;

		public void dbInit1() {
			if (!isUserExist("won")) {

				User user = createMember("jamwondong", true, "0000", "010-2111-2222", "won", "junwon");
				em.persist(user);

			}

		}

		private User createMember(String address, boolean disabled, String password, String phone, String userid,
				String username) {
			User us = new User();
			us.setAddress(address);
			us.setDisabled(disabled);
			us.setPassword(password);
			us.setPhone(phone);
			us.setUsername(username);
			us.setUserid(userid);
			return us;

		}

		private boolean isUserExist(String userid) {

			String jpql = "select u from User u where u.userid = :userid";
			TypedQuery<User> query = em.createQuery(jpql, User.class);
			query.setParameter("userid", userid);
			return !query.getResultList().isEmpty();
		}

	}

}