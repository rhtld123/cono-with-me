package com.go.conowithme.api.recruitment.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentParticipationRequest {

    private String comment;

    private RecruitmentParticipationRequest(String comment) {
        this.comment = comment;
    }

    public static RecruitmentParticipationRequest from(String comment) {
        return new RecruitmentParticipationRequest(comment);
    }
}
