package com.go.conowithme.api.user.exception;

public class UserNotFoundException extends RuntimeException{

    private static final String MESSAGE = "해당 회원이 존재하지 않습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public static UserNotFoundException thrown() {
        return new UserNotFoundException();
    }
}
