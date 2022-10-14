package com.go.conowithme.infrastructure.jwt;

import com.go.conowithme.infrastructure.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final Long accessTokenExpireTime;
    private final Long refreshTokenExpireTime;
    private final Key key;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.accessTokenExpireTime = jwtProperties.getAccessTokenExpireTime();
        this.refreshTokenExpireTime = jwtProperties.getRefreshTokenExpireTime();
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

    public TokenDto createToken(String email, String nickname,
        Collection<? extends GrantedAuthority> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        claims.put("nickname", nickname);
        Date now = new Date();

        String accessToken = Jwts.builder()
            .setClaims(claims) //Claims
            .setIssuedAt(now)   //토큰 발행 일자
            .setExpiration(
                new Date(now.getTime() + accessTokenExpireTime)) // 토큰 만료 시간
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        String refreshToken = Jwts.builder()
            .setExpiration(new Date(now.getTime() + refreshTokenExpireTime))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        return TokenDto.of(accessToken, refreshToken);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new IllegalArgumentException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("JWT 토큰이 잘못되었습니다.");
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
