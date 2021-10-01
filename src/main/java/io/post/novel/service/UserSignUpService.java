package io.post.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.post.novel.dto.UserRequest;
import io.post.novel.mapper.UserSignUpMapper;

@Service
public class UserSignUpService {
	
	@Autowired
	UserSignUpMapper userSignUpMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Transactional
	public void create(UserRequest userAdd) {
		
		//パスワード暗号化
		String rawPassword = userAdd.getPassword();
		userAdd.setPassword(passwordEncoder.encode(rawPassword));
		
		int insertResult = 0;
		insertResult += userSignUpMapper.create(userAdd);
		insertResult += userSignUpMapper.createRole(userAdd);
		
		if(insertResult != 2) {
			throw new RuntimeException();
		}
	}
	
}
