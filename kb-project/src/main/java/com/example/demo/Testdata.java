package com.example.demo;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
 
@Component
@Transactional
public class Testdata {

	private final EntityManager em;

	public Testdata(EntityManager em) {
		this.em = em;
	}

	public void dbInit1() {
		if (!isUserExist("won")) {
			User user = createMember("jamwondong", true, "0000", "010-2111-2222", "won", "junwon");
			em.persist(user);

			BankAccount bankAccount = createBankAccount(user, "1", 110990L, true);
			em.persist(bankAccount);
		}
	}

	private User createMember(String address, boolean disabled, String password, String phone, String userid,
			String username) {
		User user = new User();
		user.setAddress(address);
		user.setDisabled(disabled);
		user.setPassword(password);
		user.setPhone(phone);
		user.setUsername(username);
		user.setUserid(userid);
		return user;
	}

	private BankAccount createBankAccount(User user, String accountNumber, Long amount, boolean mainAccount) {
		if (isAccountNumberExist(accountNumber)) {
			throw new IllegalArgumentException("Duplicate account number");
		}

		BankAccount bankAccount = new BankAccount();
		bankAccount.setUser(user);
		bankAccount.setAccountNumber(accountNumber);
		bankAccount.setAmount(amount);
		bankAccount.setMainAccount(mainAccount);
		return bankAccount;
	}

	private boolean isUserExist(String userid) {
		String jpql = "select u from User u where u.userid = :userid";
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		query.setParameter("userid", userid);
		return !query.getResultList().isEmpty();
	}

	private boolean isAccountNumberExist(String accountNumber) {
		String jpql = "select b from BankAccount b where b.accountNumber = :accountNumber";
		TypedQuery<BankAccount> query = em.createQuery(jpql, BankAccount.class);
		query.setParameter("accountNumber", accountNumber);
		return !query.getResultList().isEmpty();
	}
}
