package com.example.demo.entity;

import java.util.Date;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@PrePersist
	protected void onCreate() {
		createdDate = new Date();
	}

	// getters and setters
}
