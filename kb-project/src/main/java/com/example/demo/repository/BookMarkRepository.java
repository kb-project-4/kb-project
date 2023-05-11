package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BookMark;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

	BookMark findByBanknumber(String banknumber);

	BookMark findByName(String name);

	BookMark deleteByBanknumber(String banknumber);

}
