package com.go.conowithme.api.user.service;

import com.go.conowithme.api.user.exception.RefreshTokenExpiredException;
import com.go.conowithme.infrastructure.common.redis.RedisRepository;
import com.go.conowithme.infrastructure.jwt.JwtTokenProvider;
import com.go.conowithme.infrastructure.jwt.TokenDto;
import com.go.conowithme.infrastructure.properties.JwtProperties;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;
    private final JwtProperties jwtProperties;
    private static final String REFRESH_TOKEN_PREFIX = "REFRESH_TOKEN:";

    public TokenDto login(String email, String password) {
        Authentication authentication = getAuthentication(email, password);
        TokenDto tokenDto = jwtTokenProvider.createToken(authentication);
        saveRefreshToken(authentication.getName(), tokenDto);
        return tokenDto;
    }

    public TokenDto refresh(String accessToken, String refreshToken) {
        validateRefreshToken(refreshToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String redisRefreshToken = findRefreshTokenByAuthentication(authentication);

        if (!refreshToken.equals(redisRefreshToken)) {
            throw new IllegalArgumentException("RefreshToken이 일치하지 않습니다.");
        }

        TokenDto tokenDto = jwtTokenProvider.createToken(authentication);
        saveRefreshToken(authentication.getName(), tokenDto);

        return tokenDto;
    }

    private Authentication getAuthentication(String email, String password) {
        return authenticationManagerBuilder.getObject()
            .authenticate(createUsernamePasswordAuthenticationToken(email, password));
    }

    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(
        String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            email, password);
        return authenticationToken;
    }

    private void saveRefreshToken(String id, TokenDto tokenDto) {
        redisRepository.save(REFRESH_TOKEN_PREFIX + id, tokenDto.getRefreshToken(),
            jwtProperties.getRefreshTokenExpireTime(),
            TimeUnit.MILLISECONDS);
    }

    private void validateRefreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw RefreshTokenExpiredException.thrown();
        }
    }

    private String findRefreshTokenByAuthentication(Authentication authentication) {
        return redisRepository.getValueByKey(REFRESH_TOKEN_PREFIX + authentication.getName())
            .orElseThrow(() -> new IllegalStateException("로그아웃된 사용자입니다."));
    }
}
