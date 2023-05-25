package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BookMark;
import com.example.demo.entity.User;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

	BookMark findByBookMarkAccountNumber(String banknumber);

	BookMark findByBookMarkName(String name);
	
	BookMark deleteByid(Long id);

	BookMark findByid(Long id);

	List<BookMark> findAllByUser(User user);

}