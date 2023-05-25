package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BookMarkDto;
import com.example.demo.entity.BookMark;
import com.example.demo.entity.User;
import com.example.demo.repository.BookMarkRepository;

@Service
public class BookMarkService {

	private final BookMarkRepository bookMarkRepository;

	public BookMarkService(BookMarkRepository bookMarkRepository) {
		this.bookMarkRepository = bookMarkRepository;
	}

	public BookMark createBookMark(BookMarkDto bookMark) {
		BookMark bookMark2 = bookMark.toEntity();
		return bookMarkRepository.save(bookMark2);
	}

	public void deleteBookMark(Long id) {
		bookMarkRepository.deleteById(id);
	}

	public List<BookMark> getAllBookmarks() {
		return bookMarkRepository.findAll();
	}

	public List<BookMark> getUserAllBookmarks(String userid,User user) {

		
		List<BookMark> bookMarks = bookMarkRepository.findAllByUser(user);
		return bookMarks;

	}

	public BookMark getBookmarkByBankNumber(String accountnumber) {
		return bookMarkRepository.findByBookMarkAccountNumber(accountnumber);
	}

	public BookMark getBookMarkById(Long id) {
		return bookMarkRepository.findById(id).orElse(null);
	}

	public BookMark updateBookMark(Long id, BookMarkDto updatedBookMark) {
		BookMark existingBookMark = bookMarkRepository.findById(id).orElse(null);

		if (existingBookMark != null) {
			existingBookMark.setBookMarkAccountNumber(updatedBookMark.getBookMarkAccountNumber());
			existingBookMark.setBookMarkBankname(updatedBookMark.getBookMarkName());

			// Set other properties as needed
			return bookMarkRepository.save(existingBookMark);
		}
		return null;
	}

	@Transactional
	public BookMark findBookMarkByName(String name) {
		System.out.println(bookMarkRepository.findByBookMarkName(name));
		return bookMarkRepository.findByBookMarkName(name);
	}

}