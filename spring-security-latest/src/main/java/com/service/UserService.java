package com.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.UserInfo;
import com.repository.UserInfoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

	private UserInfoRepository repository;
	
	private PasswordEncoder passwordEncoder;

	public String addUser(UserInfo userInfo) {
		String name = userInfo.getName();
		UserInfo obj1 = repository.findByName(name).orElse(null);
		System.out.println(obj1);
		if (obj1 == null) {
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			repository.save(userInfo);
			return "Registration Successfully ";
		} else {
			return "This UserName is Already Registered.";
		}
	}

	public String getRoles(String username) {
		UserInfo obj2 = repository.findByName(username).orElse(null);
		if (obj2 != null) {
			return obj2.getRoles();
		}
		return "Not Found";
	}
}
