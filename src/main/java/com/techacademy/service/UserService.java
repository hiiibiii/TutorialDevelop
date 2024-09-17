package com.techacademy.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.User;
import com.techacademy.repository.UserRepository;

@Service
public class UserService{
	private final UserRepository userRepository;

	public UserService(UserRepository repository) {
		this.userRepository = repository;
	}

	/** 全件を検索して返す */
	public List<User> getUserList(){
		// リポジトリのfindAllメソッドを呼び出す
		return userRepository.findAll();
	}
	//Lesson 18Chapter 7追加
	@Transactional
	public User saveUser(User user) { //saveUserはデータベース更新用のメソッド
		return userRepository.save(user);
	}
}