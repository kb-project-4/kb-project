package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.dto.BookMarkDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookMark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String accountNumber;

	private String bankname;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user")
//	private User user;

	public BookMarkDto toDto() {
		BookMarkDto dto = new BookMarkDto();
		dto.setName(this.name);
		dto.setAccountNumber(this.accountNumber);
		dto.setBankname(this.bankname);
//		dto.setUser(this.user);
		return dto;
	}

}
