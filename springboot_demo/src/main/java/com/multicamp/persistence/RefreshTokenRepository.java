package com.multicamp.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multicamp.domain.RefreshTokenEntity;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long>{
	
	Optional<RefreshTokenEntity> findByUserIdx(Long userIdx);
	
	Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
	

}
 