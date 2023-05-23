package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.BookMarkService;
import com.example.demo.Service.UserService;
import com.example.demo.dto.BookMarkDto;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.BookMark;
import com.example.demo.entity.User;

@Controller
@RequestMapping("/bookmarks")
public class BookMarkController {

	@Autowired
	private BookMarkService bookMarkService;

	@Autowired
	private UserService userService;
//
//	@GetMapping
//	public String getAllBookMarks(Model model, HttpServletRequest request, HttpSession session) {
//		session = request.getSession();
//		User user = (User) session.getAttribute("user");
//		String userid = user.getUserid();
//
//		List<BookMark> bookMarks = bookMarkService.getAllBookmarks();
//		model.addAttribute("bookMarks", bookMarks);
//		model.addAttribute("userid", userid);
//
//		return "BookMark/bookMarks";
//
//	}

	@GetMapping
	public String getUserBookMarks(Model model, HttpServletRequest request, HttpSession session) {
		System.out.println("here");
		session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userid = user.getUserid();
		System.out.println("userid" + userid);

		List<BookMark> bookMarks = bookMarkService.getUserAllBookmarks(userid);
		model.addAttribute("bookMarks", bookMarks);
		model.addAttribute("userid", userid);

		System.out.println("here2");
		return "BookMark/bookMark";

	}

	@GetMapping("/create")
	public String createBookMarkForm(Model model, HttpSession session, HttpServletRequest request) {

		BookMark bookMark = new BookMark();
		session = request.getSession();

		User user = (User) session.getAttribute("user");
		String userid = user.getUserid();

		System.out.println("userid" + userid);
		User user1 = userService.getUserByUserId(userid);
		System.out.println("username" + user1.getUsername());

		bookMark.setUser(user1);

		model.addAttribute("bookMark", bookMark);

		return "BookMark/bookMarkForm";
	}

	@PostMapping("/create")
	public String createBookMark(@ModelAttribute("bookMark") BookMarkDto bookMark, HttpSession session,
			HttpServletRequest request) {

		session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println(user.toString());
		String userid = user.getUserid();

		System.out.println("userid_create" + userid);

		BookMark bookMark2 = bookMark.toEntity();

		if (userService.getUserByUsername(bookMark2.getName()) != null) {// db에 잇는 유저

			User user1 = userService.getUserByUsername(bookMark2.getName());

			for (BankAccount bankAccount : user1.getBankAccounts()) {
				if (bankAccount.getBank().getBankname().equals(bookMark2.getBankname())) {// 해당유저의 계좌가 폼에서 입력한 계좌 이름과
																							// 같은경우

					bookMarkService.createBookMark(bookMark);

					return "redirect:/bookmarks";
				}
			}

			System.out.println("계좌를 똑바로 입력");

		}

		System.out.println("해당사용자 없음");
		return "redirect:create";
	}

	@GetMapping("/edit/{id}")
	public String editBookMark(@PathVariable("id") Long id, Model model) {
		BookMark bookMark = bookMarkService.getBookMarkById(id);

		if (bookMark != null) {
			model.addAttribute("bookMark", bookMark);
			return "BookMark/bookMarkFormEdit";
		} else {
			// Handle error when bookmark is not found
			return "redirect:/bookmarks";
		}
	}

	@PostMapping("/edit/{id}")
	public String updateBookMark(@PathVariable("id") Long id, @ModelAttribute("bookMark") BookMarkDto updatedBookMark) {
		BookMark bookMark = bookMarkService.updateBookMark(id, updatedBookMark);
		if (bookMark != null) {
			return "redirect:/bookmarks";
		}
		return "error";
	}

	@GetMapping("/delete/{id}")
	public String deleteBookMark(@PathVariable("id") Long id) {
		// Delete the bookmark from the database
		bookMarkService.deleteBookMark(id);

		return "redirect:/bookmarks";
	}

}
