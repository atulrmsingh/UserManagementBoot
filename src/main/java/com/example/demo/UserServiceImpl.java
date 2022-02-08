package com.example.demo;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired

	UserRepository userRepositry;

	@Override
	public List<UserEntity> getAllUsers() {
		return userRepositry.findAll();
	}

	@Override
	public UserEntity addUser(UserEntity user) {
		return userRepositry.save(user);
	}

	@Override
	public UserEntity getUserById(int id) {
		return userRepositry.getById(id);
	}

	@Override
	public void deleteUserById(int id) {
		userRepositry.deleteById(id);

	}

	@Override
	public UserEntity updateUserById(int id, UserEntity user) {
		UserEntity userEntity = userRepositry.getById(id);
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setMobile(user.getMobile());
		return userRepositry.save(userEntity);

	}

	@Override
	public String login(String userName, String password) {
		List<UserEntity> userList = userRepositry.findAll();
		for (UserEntity userEntity : userList) {
			if (userEntity.getUserName().equals(userName)) {
				if (userEntity.getPassword().equals(password)) {
					return "loggedin";
				}

			}

		}

		return "Invalid user name or password";
	}

	@Override
	public String forgotPassword(String userName) {
		List<UserEntity> userList = userRepositry.findAll();
		for (UserEntity userEntity : userList) {
			if (userEntity.getUserName().equals(userName)) {
				sendEmail(userEntity);
				return "otp sent to your email";
			}
		}

		return "no user found";
	}

	@Override
	public String generateOtp() {
		String numbers = "1234567890";
		Random random = new Random();
		char[] otp = new char[6];

		for (int i = 0; i < 6; i++) {
			otp[i] = numbers.charAt(random.nextInt(numbers.length()));
		}
		return otp.toString();

	}

	@Override
	public void sendEmail(UserEntity user) {

		Runnable task = new Runnable() {

			@Override
			public void run() {
				String from = "singh.atulsingh.atul959817@gmail.com";
				final String username = "singh.atulsingh.atul959817@gmail.com";
				final String password = "atuls9598176328";
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
				props.put("mail.debug", "true");

				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
				try {
					Thread.sleep(1000);
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getUserName()));
					message.setSubject("Forgot Password OTP");
					String msg = "Your OTP is "+ generateOtp();
					message.setContent(msg, "text/html;charset=utf-8");
					Transport.send(message);
				} catch (MessagingException | SecurityException | InterruptedException exception) {
					exception.printStackTrace();
				}
			}
		};
		new Thread(task).start();

	}

}
