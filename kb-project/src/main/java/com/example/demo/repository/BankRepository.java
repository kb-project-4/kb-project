package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Bank;
import com.example.demo.entity.BookMark;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

	Bank findByBankname(String bankname );
}
