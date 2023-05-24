package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BookMark;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

	BookMark findByAccountNumber(String banknumber);

	BookMark findByName(String name);

	BookMark deleteByid(Long id);

	BookMark findByid(Long id);

	List<BookMark> findAllByUserid(String userid);

}
