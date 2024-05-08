package com.multicamp.service;

import javax.inject.Inject;

import org.apache.el.stream.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.multicamp.domain.UserEntity;
import com.multicamp.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service(value = "userJpaService")
@Slf4j
public class UserJpaService {
	
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
			log.warn("nickname 은 이미 있습니다 {}", nickname);
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
	
	public UserEntity findById(Long idx) {
		 return userRepository.findById(idx).get();
	}
	

}
