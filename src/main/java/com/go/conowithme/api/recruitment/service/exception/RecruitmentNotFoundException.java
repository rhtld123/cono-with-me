package com.go.conowithme.api.recruitment.service.exception;

public class RecruitmentNotFoundException extends Exception {
    private static final RecruitmentNotFoundException INSTANCE = new RecruitmentNotFoundException("해당 공고가 존재하지 않습니다.");

    public RecruitmentNotFoundException(String message) {
        super(message);
    }

    public static RecruitmentNotFoundException thrown() {
        return INSTANCE;
    }
}
