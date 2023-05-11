package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.BookMark;
import com.example.demo.entity.User;
import com.example.demo.repository.BookMarkRepository;
import com.example.demo.repository.UserRepository;

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

//
//	public BookMark updateBookMark(String banknubmer, String name,BookMark bookMark) {
//		
//		Optional<BookMark> optionalBookmark = bookMarkRepository.findbyBanknumber(banknumber) ;
//		
//		if (optionalBookmark.isPresent()) {
//
//			BookMark  bMark = optionalBookmark.get();
//			bMark.set)
//			
//			user.setAddress(userDto.getAddress());
//
//			return userRepository.save(user);
//		}
//
//		return null;
//	}
//	
//	

	public void deleteBookMark(String bankname, String banknumber) {

		bookMarkRepository.deleteByBanknumber(banknumber);

	}

	public List<BookMark> getAllbookmarks() {
		return bookMarkRepository.findAll();
	}

	public BookMark getbookmarkbybanknumber(String banknumber) {
		return bookMarkRepository.findByBanknumber(banknumber);
	}

}
