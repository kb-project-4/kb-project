package com.example.demo.dto;

import com.example.demo.entity.Log;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
public class TransferDto {

	private User sender;
	private String recipient_name;
	private String recipient_banknumber;
	private String category;
	private String sender_banknumber;
	private String account_password;

	private Long amount;

	public Log toEntity() {

		Log log = new Log();
		log.setUser(sender);
		log.setAmount(amount);
		log.setMy_banknumber(sender_banknumber);
		log.setRecipient_banknumber(recipient_banknumber);
		log.setRecipient_name(recipient_name);
		log.setCategory(category);
		

		
		return log;
	}

}
