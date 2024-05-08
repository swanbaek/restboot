package com.multicamp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multicamp.domain.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	UserEntity findByNickname(String nickname);
	Boolean existsByNickname(String nickname);
	UserEntity findByNicknameAndPwd(String nickname, String pwd);

}
