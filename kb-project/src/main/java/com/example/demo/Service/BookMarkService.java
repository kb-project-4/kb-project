package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

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
	
	public BookMark findBookMarkByName(String name) {
		return bookMarkRepository.findByBookMarkName(name);
	}

	public void deleteBookMark(Long id) {
		bookMarkRepository.deleteById(id);
	}

	public List<BookMark> getAllBookmarks() {
		return bookMarkRepository.findAll();
	}

	public List<BookMark> getUserAllBookmarks(String userid) {

		List<BookMark> bookMarks = bookMarkRepository.findAll();
		List<BookMark> bookMarks2 = new ArrayList<BookMark>();

		for (BookMark bookMark : bookMarks) {
			if (userid.equals(bookMark.getUser().getUserid()))
				bookMarks2.add(bookMark);

		}

		return bookMarks2;

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
			existingBookMark.setBookMarkAccountNumber(updatedBookMark.getAccountNumber());
			existingBookMark.setBookMarkBankname(updatedBookMark.getName());
					
			// Set other properties as needed
			return bookMarkRepository.save(existingBookMark);
		}
		return null;
	}

}
