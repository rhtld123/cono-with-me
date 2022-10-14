package com.go.conowithme.infrastructure.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    private TokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenDto of(String accessToken, String refreshToken) {
        return new TokenDto(accessToken, refreshToken);
    }
}
