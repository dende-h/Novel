package io.post.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.post.novel.mapper.LoginMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	LoginMapper loginMapper;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*
		//ユーザー情報取得
		UserForm loginUser = LoginService.getLoginUser(username);
		
		
		//ユーザーが存在しない場合
		if(loginUser == null){
			
			throw new UsernameNotFoundException("user not found");
			
		}
		
		//権限List生成
		GrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getRole());
		List<GrantedAuthority>authorities = new ArrayList<>();
		authorities.add(authority);
		
		//UserDitails生成
		UserDetails userDetails = (UserDetails) new User(loginUser.getPenName(),loginUser.getPassword(),authorities);
		
		*/
		
		return loginMapper.identifyUser(username);
	}

}
