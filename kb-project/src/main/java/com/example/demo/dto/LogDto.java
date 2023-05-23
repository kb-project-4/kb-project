package com.example.demo.dto;

import com.example.demo.entity.Log;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LogDto {

	private User user;
	private String recipient_name;
	private String recipient_banknumber;
	private String category;
	private String my_banknumber;

	private Long amount;

	public Log toEntity() {

		Log log = new Log();
		log.setUser(user);
		log.setAmount(amount);
		log.setMy_banknumber(my_banknumber);
		log.setRecipient_banknumber(recipient_banknumber);
		log.setRecipient_name(recipient_name);
		log.setCategory(category);

		
		return log;
	}

}
