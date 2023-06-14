package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BookMarkDto;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.BookMark;
import com.example.demo.entity.User;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.BookMarkRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BookMarkService {

	private final BookMarkRepository bookMarkRepository;
	private final BankAccountRepository bankAccountRepository;
	private final UserRepository userRepository;

	public BookMarkService(BookMarkRepository bookMarkRepository, BankAccountRepository bankAccountRepository,
			UserRepository userRepository) {
		this.bookMarkRepository = bookMarkRepository;
		this.bankAccountRepository = bankAccountRepository;
		this.userRepository = userRepository;
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

	public List<BookMark> getAllBookMarkUserName(User user) {
		return bookMarkRepository.findAllByUser(user);
	}

	public List<BookMark> getUserAllBookmarks(String userid, User user) {

		List<BookMark> bookMarks = bookMarkRepository.findAllByUser(user);
		return bookMarks;

	}

	public BookMark getBookmarkByBankNumber(String accountnumber) {
//		return bookMarkRepository.findByBookMarkAccountNumber(accountnumber);
		BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountnumber);
		return bookMarkRepository.findBybankAccount(bankAccount);
	}

	public BookMark getBookMarkById(Long id) {
		return bookMarkRepository.findById(id).orElse(null);
	}

	public BookMark updateBookMark(Long id, BookMarkDto updatedBookMark) {
		BookMark existingBookMark = bookMarkRepository.findById(id).orElse(null);

		if (existingBookMark != null) {
//			existingBookMark.setBookMarkBankname(updatedBookMark.getBookMarkBankname());
//			existingBookMark.setBookMarkAccountNumber(updatedBookMark.getBookMarkAccountNumber());
//			existingBookMark.setBookMarkName(updatedBookMark.getBookMarkName());

			existingBookMark.setBankAccount(updatedBookMark.getBankAccount());

			// Set other properties as needed
			return bookMarkRepository.save(existingBookMark);
		}
		return null;
	}
//
//	@Transactional
//	public BookMark findBookMarkByName(String name) {
//		return bookMarkRepository.findByBookMarkName(name);
//	}

	@Transactional
	public BookMark findBookMarkBybankAccount(BankAccount bankAccount) {
		return bookMarkRepository.findBybankAccount(bankAccount);
	}
//
//	public BookMark findByUserAndBookMarkName(User user, String bookMarkName) {
//		return bookMarkRepository.findByUserAndBookMarkName(user, bookMarkName);
//	}

	public BookMark findByUserAndname(User user, String name) {

		User bankuser = userRepository.findByUsername(name);
		BankAccount bankAccount = bankAccountRepository.findByUser(bankuser);

		return bookMarkRepository.findByUserAndBankAccount(user, bankAccount);
	}

}