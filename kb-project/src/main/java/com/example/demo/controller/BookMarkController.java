package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Service.BookMarkService;
import com.example.demo.entity.BookMark;

@Controller
@RequestMapping("/bookmarks")
public class BookMarkController {

	@Autowired
	private BookMarkService bookMarkService;

	@GetMapping("/{banknumber}")
	public String getBookMark(@PathVariable("banknumber") String banknumber, Model model) {
		BookMark bookMark = bookMarkService.getbookmarkbybanknumber(banknumber);

		model.addAttribute("bookMark", bookMark);
		return "BookMark/bookMark";
	}

	@GetMapping
	public String getAllBookMarks(Model model) {
		List<BookMark> bookMarks = bookMarkService.getAllbookmarks();
		model.addAttribute("bookMarks", bookMarks);
		return "BookMark/bookMarks";
	}

	@GetMapping("/create")
	public String createBookMarkForm(Model model) {
		BookMark bookMark = new BookMark();
		model.addAttribute("bookMark", bookMark);
		return "BookMark/bookMarkForm";
	}

	@PostMapping("/create")
	public String createBookMark(@ModelAttribute("bookMark") BookMark bookMark) {
		bookMarkService.createBookMark(bookMark);
		return "redirect:/bookmarks";
	}

//
//	@GetMapping("/{id}/update")
//	public String updateBookMarkForm(@PathVariable("id") Long id, Model model) {
//		BookMark bookMark = bookMarkService.getBookMark(id);
//		model.addAttribute("bookMark", bookMark);
//		return "bookMarkForm";
//	}
//
//	@PostMapping("/{id}/update")
//	public String updateBookMark(@PathVariable("id") Long id, @ModelAttribute("bookMark") BookMark bookMark) {
//		bookMark.setId(id);
//		bookMarkService.updateBookMark(bookMark);
//		return "redirect:/bookmarks";
//	}

	@GetMapping("/{banknumber}/delete")
	public String deleteBookMark(@PathVariable("banknumber") String banknumber, String name) {
		bookMarkService.deleteBookMark(name, banknumber);
		return "redirect:/bookmarks";

	}


}
