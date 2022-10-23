package com.go.conowithme.api.user.exception;

public class RefreshTokenExpiredException extends RuntimeException {

    private static final String MESSAGE = "Refresh Token이 만료되었습니다.";

    public RefreshTokenExpiredException() {
        super(MESSAGE);
    }

    public static RefreshTokenExpiredException thrown() {
        return new RefreshTokenExpiredException();
    }
}
