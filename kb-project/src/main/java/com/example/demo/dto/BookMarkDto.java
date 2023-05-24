package com.example.demo.dto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.entity.BookMark;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BookMarkDto {

	private String name;

	private String accountNumber;

	private String bankname;

	private User user;

	public BookMark toEntity() {

		BookMark bookMark = new BookMark();
		bookMark.builder().name(this.name).bankname(this.bankname).accountNumber(this.accountNumber).build();
		return bookMark;
	}

}
