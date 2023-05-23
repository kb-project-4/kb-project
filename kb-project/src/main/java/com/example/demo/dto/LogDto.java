package com.example.demo.dto;

import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LogDto {
//
//	private long amount;
//	private String recipeint_name;
//	private String recipeint_banknumber;
//	private User user;

	private User user;

	private String recipient_name;
	private String recipient_banknumber;
	private String category;
	private String my_banknumber;

	private Long amount;

}
