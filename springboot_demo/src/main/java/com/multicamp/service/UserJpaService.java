package com.multicamp.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.multicamp.domain.UserEntity;
import com.multicamp.persistence.UserRepository;

@Service(value = "userJpaService")
public class UserJpaService {
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity==null||userEntity.getNickname()==null) {
			throw new RuntimeException("Invalid arguments");			
		}
		final String nickname=userEntity.getNickname();
		if(userRepository.existsByNickname(nickname)) {
			logger.warn("nickname 은 이미 있습니다 {}", nickname);
			throw new RuntimeException("Nickname already exists");
		}
		userEntity.setPwd(passwordEncoder.encode(userEntity.getPwd()));
		return userRepository.save(userEntity);
	}//-----------------------------------
	
	public UserEntity getByCredentials(final String nickname, final String pwd) {
		//return userRepository.findByNicknameAndPwd(nickname, pwd); <=암호화하지 않았을 때 사용
		final UserEntity originUser=userRepository.findByNickname(nickname);
		if(originUser!=null && passwordEncoder.matches(pwd, originUser.getPwd())) {
			return originUser;
		}
		return null;
	}
	

}
