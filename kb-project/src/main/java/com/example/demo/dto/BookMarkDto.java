package com.example.demo.dto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.BookMark;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookMarkDto {
//
//	private String bookMarkName;
//
//	private String bookMarkAccountNumber;
//
//	private String bookMarkBankname;

	private BankAccount bankAccount;

	private User user;

	public BookMark toEntity() {

		BookMark bookMark = BookMark.builder()
				.bankAccount(this.bankAccount)
//				.bookMarkName(this.bookMarkName)
//				.bookMarkBankname(this.bookMarkBankname)
//				.bookMarkAccountNumber(this.bookMarkAccountNumber)
				.user(user).

				build();
		return bookMark;
	}

}
