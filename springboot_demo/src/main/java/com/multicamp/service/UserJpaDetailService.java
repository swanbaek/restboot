package com.multicamp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.multicamp.domain.SecurityReactUser;
import com.multicamp.domain.UserEntity;
import com.multicamp.domain.UserVO;
import com.multicamp.persistence.UserRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class UserJpaDetailService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEnty=userRepository.findByNickname(username)
		if(userEnty==null) {
			throw new UsernameNotFoundException(username+"란 회원은 없습니다");
		}
		
		return new SecurityReactUser(userEnty);
	}

}
