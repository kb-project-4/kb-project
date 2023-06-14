package com.example.demo.entity;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.example.demo.dto.BankAccountDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankAccount extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long amount;

	@Column(unique = true)
	private String accountNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id")
	private Bank bank;

	private boolean mainAccount;

	@OneToMany(mappedBy = "bankAccount", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<BookMark> bookmarks = new ArrayList<BookMark>();

	public BankAccountDto toDto() {
		BankAccountDto dto = BankAccountDto.builder().id(this.id).accountNumber(this.accountNumber).amount(this.amount)
				.user(this.user).bank(this.bank).mainAccount(this.mainAccount).build();
		return dto;
	}

}