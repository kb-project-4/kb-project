package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long id;
	private String username;
	private String userid;
	private String password;
	private String phone;
	private String address;

	private boolean disabled;

	private List<BankAccount> bankAccounts = new ArrayList<>();

	public User toEntity() {
		return User.builder().username(username).userid(userid).password(password).phone(phone).address(address)
				.disabled(disabled).bankAccounts(bankAccounts).build();

	}
}
