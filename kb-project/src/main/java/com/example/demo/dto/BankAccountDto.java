package com.example.demo.dto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.entity.Bank;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankAccountDto {
	private Long id;

	private Long amount;

	private String accountNumber;

	private User user;

	private Bank bank;
	private boolean mainAccount;

	public BankAccount toEntity() {
		return BankAccount.builder().accountNumber(this.accountNumber).amount(this.amount).bank(this.bank).id(this.id)
				.build();
	}

}
