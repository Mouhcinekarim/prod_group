package com.gestion_pfe.prod.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gestion_pfe.prod.exceptions.PFE_RegistrationException;
import com.gestion_pfe.prod.model.RefrechToken;
import com.gestion_pfe.prod.repositoy.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;

    public RefrechToken generateRefreshToken() {
        RefrechToken refreshToken = new RefrechToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new PFE_RegistrationException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
