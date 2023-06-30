package com.multicamp.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.multicamp.domain.SecurityUser;
import com.multicamp.domain.UserVO;
import com.multicamp.mapper.UserMapper;

@Service("loginValidatorService")
public class LoginValidatorService implements UserDetailsService{

	Logger log =LoggerFactory.getLogger(getClass());
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Inject
	private UserMapper userMapper;
	
	//UserDetailsService의 추상메서드 재정의
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO tmp_user=new UserVO();
		tmp_user.setUserid(username);
		UserVO user=userMapper.findUser(tmp_user);
		if(user==null)
			throw new UsernameNotFoundException(username+"란 회원은 없습니다");
		String passwd=user.getPasswd();
		String role=user.getRole();
		log.info("role: "+role);
		/*
		return User.builder() //====>User를 커스텀하여 SecurityUser로 구현함
					.username(username)
					.password(user.getPasswd())
					.roles(role)
					.build();
		
		*/
		return new SecurityUser(user);
	}
}
