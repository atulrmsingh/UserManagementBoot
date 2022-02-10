package com.example.demo;

import java.util.List;

public interface UserService {
	public List<UserEntity> getAllUsers();

	public String addUser(UserEntity user);

	public UserEntity getUserById(int id);

	public void deleteUserById(int id);

	public UserEntity updateUserById(int id, UserEntity user);

	public String login(String userName, String password);

	public String forgotPassword(String userName);

	public String generateOtp();

	public void sendEmail(UserEntity user);

}
