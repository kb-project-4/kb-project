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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.ToString;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookMark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String bookMarkName;

	private String bookMarkAccountNumber;

	private String bookMarkBankname;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;

	public BookMarkDto toDto() {
		BookMarkDto dto = new BookMarkDto();
		dto.setBookMarkName(this.bookMarkName);
		dto.setBookMarkAccountNumber(this.bookMarkAccountNumber);
		dto.setBookMarkBankname(this.bookMarkBankname);
		dto.setUser(this.user);
		return dto;
	}

}