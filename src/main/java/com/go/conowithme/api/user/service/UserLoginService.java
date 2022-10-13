package com.go.conowithme.api.user.service;

import com.go.conowithme.api.user.domain.entity.UserEntity;
import com.go.conowithme.api.user.service.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;

    public String login(UserLoginDto loginDto) {
        UserEntity userEntity = findByEmailAndDeletedAtIsNull(loginDto.getEmail());
        validatePassword(userEntity.getPassword(), loginDto.getPassword());
        return createToken();
    }

    private UserEntity findByEmailAndDeletedAtIsNull(String email) {
        return userQueryService.findByEmailAndDeletedAtIsNull(email);
    }

    private void validatePassword(String inputPassword, String entityPassword) {
        if (!passwordEncoder.matches(inputPassword, entityPassword)) {
            throw new IllegalStateException("비밀번호가 다릅니다.");
        }
    }

    private String createToken() {
        return "";
    }
}
