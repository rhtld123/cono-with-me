package com.go.conowithme.api.user.exception;

public class UserNotFoundException extends RuntimeException{

    private static final String MESSAGE = "해당 회원이 존재하지 않습니다.";
    private String property;
    private String value;

    public UserNotFoundException(String property, String value) {
        super(MESSAGE);
        this.property = property;
        this.value = value;
    }

    public static UserNotFoundException thrown(String property, String value) {
        return new UserNotFoundException(property, value);
    }
}
