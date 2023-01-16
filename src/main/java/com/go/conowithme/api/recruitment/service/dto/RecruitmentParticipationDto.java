package com.go.conowithme.api.recruitment.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentParticipationDto {

    private Long recruitmentId;
    private Long participationId;
    private String comment;
    private LocalDateTime requestedAt;

    private RecruitmentParticipationDto(Long recruitmentId, Long participationId, String comment, LocalDateTime requestedAt) {
        this.recruitmentId = recruitmentId;
        this.participationId = participationId;
        this.comment = comment;
        this.requestedAt = requestedAt;
    }

    public static RecruitmentParticipationDto of(Long recruitmentId, Long participationId, String comment, LocalDateTime requestedAt) {
        return new RecruitmentParticipationDto(recruitmentId, participationId, comment, requestedAt);
    }
}
