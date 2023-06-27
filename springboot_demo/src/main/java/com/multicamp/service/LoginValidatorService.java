package com.multicamp.service;

import javax.annotation.Resource;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("loginValidatorService")
public class LoginValidatorService implements UserDetailsService{

	@Resource(name="passwordEncoder")
	private PasswordEncoder passwordEncoder;
	//UserDetailsService의 추상메서드 재정의
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
