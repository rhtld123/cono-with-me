package com.go.conowithme.api.user.service;

import com.go.conowithme.api.user.domain.entity.Authority;
import com.go.conowithme.api.user.domain.entity.UserEntity;
import com.go.conowithme.api.user.exception.UserNotFoundException;
import com.go.conowithme.api.user.service.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public void signup(UserSignupDto userSignupDto) {
        validateAlreadySignupUser(userSignupDto.getEmail());
        userCommandService.save(createUserEntity(userSignupDto));
    }

    private void validateAlreadySignupUser(String email) {
        if (findByEmailAndDeletedAtIsNull(email) != null) {
            throw new IllegalArgumentException("이미 회원가입된 이메일입니다.");
        }
    }

    private UserEntity findByEmailAndDeletedAtIsNull(String email) {
        try {
            return userQueryService.findByEmailAndDeletedAtIsNull(email);
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    private UserEntity createUserEntity(UserSignupDto userSignupDto) {
        UserEntity userEntity = UserEntity.of(userSignupDto.getEmail(), userSignupDto.getPassword(),
            userSignupDto.getName(), userSignupDto.getNickname(), Authority.ROLE_USER);
        return userEntity;
    }
}
