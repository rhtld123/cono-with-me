package com.go.conowithme.api.recruitment.service.exception;

public class RecruitmentParticipationNotFoundException extends Exception {
    private static final RecruitmentParticipationNotFoundException INSTANCE = new RecruitmentParticipationNotFoundException("해당 참가신청 내역이 존재하지 않습니다.");

    public RecruitmentParticipationNotFoundException(String message) {
        super(message);
    }

    public static RecruitmentParticipationNotFoundException thrown() {
        return INSTANCE;
    }
}
