package com.go.conowithme.api.user.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginDto {

    private String email;
    private String password;

    public static UserLoginDto of(String email, String password) {
        return new UserLoginDto(email, password);
    }
}
