package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService us;

	@GetMapping
	@RequestMapping("getAllUsers")
	public String getAllUsers() {
		List<UserEntity> userList = us.getAllUsers();

		return userList.toString();
	}

	@GetMapping
	@RequestMapping("getUsersByid")
	public String getUsersByid(@RequestParam("id") int id) {
		UserEntity user = us.getUserById(id);

		return user.toString();
	}

	@PostMapping
	@RequestMapping("addUser")
	public String addUser(@RequestBody UserEntity user1) {
		String user = us.addUser(user1);
		return user;
	}

	@PostMapping
	@RequestMapping("login")
	public String login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		String user = us.login(userName, password);
		return user.toString();
	}

	@PostMapping
	@RequestMapping("forgotPassword")
	public String forgotPassword(@RequestParam("userName") String userName) {
		String user = us.forgotPassword(userName);
		return user.toString();
	}

	@DeleteMapping
	@RequestMapping("deleteUser")
	public String deleteUser(@RequestParam("id") int id) {
		us.deleteUserById(id);
		return "user deleted";
	}

}
