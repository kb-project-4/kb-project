package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Login;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(UserDto user) {

		User user2 = user.toEntity();
		return userRepository.save(user2);
	}

	public User updateUser(Long id, User user) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (optionalUser.isPresent()) {
			User user1 = optionalUser.get();
			user1.setUsername(user.getUsername());
			user1.setUserid(user.getUserid());
			user1.setPassword(user.getPassword());
			user1.setPhone(user.getPhone());
			user1.setAddress(user.getAddress());

			return userRepository.save(user1);
		}

		return null;
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public User getUserByUserId(String userid) {
		User user = userRepository.findByuserid(userid);
		return user;

	}

	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;

	}

}
