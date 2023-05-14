package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BookMark;
import com.example.demo.repository.BookMarkRepository;

@Service
public class BookMarkService {

	private final BookMarkRepository bookMarkRepository;

	@Autowired
	public BookMarkService(BookMarkRepository bookMarkRepository) {
		this.bookMarkRepository = bookMarkRepository;
	}

	public BookMark createBookMark(BookMark bookMark) {
		return bookMarkRepository.save(bookMark);
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

	public BookMark getBookmarkByBankNumber(String bankNumber) {
		return bookMarkRepository.findByBanknumber(bankNumber);
	}

	public BookMark getBookMarkById(Long id) {
		return bookMarkRepository.findById(id).orElse(null);
	}

	public BookMark updateBookMark(Long id, BookMark updatedBookMark) {
		BookMark existingBookMark = bookMarkRepository.findById(id).orElse(null);

		if (existingBookMark != null) {
			existingBookMark.setBanknumber(updatedBookMark.getBanknumber());
			existingBookMark.setName(updatedBookMark.getName());
			// Set other properties as needed
			return bookMarkRepository.save(existingBookMark);
		}
		return null;
	}

}
