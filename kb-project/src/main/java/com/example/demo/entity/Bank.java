package com.example.demo.entity;

import java.util.ArrayList;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
@Builder
public class Bank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BankAccount> bankAccounts = new ArrayList<>();

	private String bankname;
	private String bankcode;
	private String branchname;

}
