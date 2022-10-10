package com.go.conowithme.modules.user.application.port.in.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupCommand {

    private String email;
    private String password;
    private String name;

    @Builder
    private UserSignupCommand(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserSignupCommand of(String email, String password, String name) {
        return UserSignupCommand.builder()
            .email(email)
            .password(password)
            .name(name)
            .build();
    }
}
