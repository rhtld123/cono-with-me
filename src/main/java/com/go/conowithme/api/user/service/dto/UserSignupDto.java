package com.go.conowithme.api.user.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSignupDto {

    private String email;
    private String password;
    private String name;
    private String nickname;

    public static UserSignupDto of(String email, String password, String name, String nickname) {
        return new UserSignupDto(email, password, name, nickname);
    }
}
