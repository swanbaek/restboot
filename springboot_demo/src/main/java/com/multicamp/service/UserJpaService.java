package com.multicamp.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.multicamp.domain.UserEntity;
import com.multicamp.persistence.UserRepository;

@Service(value = "userJpaService")
public class UserJpaService {
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Inject
	private UserRepository userRepository;
	
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity==null||userEntity.getNickname()==null) {
			throw new RuntimeException("Invalid arguments");			
		}
		final String nickname=userEntity.getNickname();
		if(userRepository.existsByNickname(nickname)) {
			logger.warn("nickname 은 이미 있습니다 {}", nickname);
			throw new RuntimeException("Nickname already exists");
		}
		return userRepository.save(userEntity);
	}//-----------------------------------
	
	public UserEntity getByCredentials(final String nickname, final String pwd) {
		return userRepository.findByNicknameAndPwd(nickname, pwd);
	}
	

}
