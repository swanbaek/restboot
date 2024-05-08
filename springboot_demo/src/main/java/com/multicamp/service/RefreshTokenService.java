package com.multicamp.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multicamp.domain.RefreshToken;
import com.multicamp.persistence.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
    @Transactional
    public RefreshToken addRefreshToken(RefreshToken entity) {
    	return refreshTokenRepository.save(entity);
    }
    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken).ifPresent(refreshTokenRepository::delete);
    }
}
