package com.multicamp.service;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.multicamp.domain.UserVO;
import com.multicamp.mapper.UserMapper;

@Service("loginValidatorService")
public class LoginValidatorService implements UserDetailsService{

	@Resource(name="bCryptPasswordEncoder")
	private PasswordEncoder passwordEncoder;
	
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
		
		return null;
	}
}
