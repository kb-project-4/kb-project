package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
	void deleteByAccountNumber(String accountnumber);

	BankAccount findByAccountNumber(String accountnumber);

	List<BankAccount> findAllByUserId(Long userid);

}
