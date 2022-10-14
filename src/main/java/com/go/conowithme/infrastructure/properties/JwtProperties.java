package com.go.conowithme.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Getter
@Setter
@ConditionalOnProperty(prefix = "jwt")
public class JwtProperties {
    private Long accessTokenExpireTime;
    private Long refreshTokenExpireTime;
    private String secret;
}
