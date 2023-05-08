package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public User createUser(User user) {

		return userRepository.save(user);
	}

//	public User updateUser(Long id, UserDto userDto) {
		public User updateUser(Long id, User userDto) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setUsername(userDto.getUsername());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setPhone(userDto.getPhone());
			user.setAddress(userDto.getAddress());

			return userRepository.save(user);
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

}
