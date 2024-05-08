package com.multicamp.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multicamp.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	
	Optional<RefreshToken> findByUserIdx(Long userIdx);
	
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
	

}
 