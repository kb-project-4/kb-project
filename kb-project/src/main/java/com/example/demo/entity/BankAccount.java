package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.example.demo.dto.BankAccountDto;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Getter

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long amount;

	private String accountNumber;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bank_id")
	private Bank bank;

	public BankAccountDto toDto() {

		BankAccountDto dto = BankAccountDto.builder().id(this.id).accountNumber(this.accountNumber).amount(this.amount)
				.user(this.user).bank(this.bank).build();

		return dto;
	}

}