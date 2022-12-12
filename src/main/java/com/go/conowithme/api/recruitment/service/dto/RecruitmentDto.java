package com.go.conowithme.api.recruitment.service.dto;

import com.go.conowithme.api.recruitment.domain.entity.Genre;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentDto {
    @Schema(description = "유저 ID")
    private Long userId;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "본문")
    private String content;
    @Schema(description = "일정 시작 일자")
    private LocalDateTime startedAt;
    @Schema(description = "모집 만료 일자")
    private LocalDateTime expiredAt;
    @Schema(description = "장소")
    private String place;
    @Schema(description = "인원 제한")
    private int participant;
    @Schema(description = "장르")
    private Genre genre;

    private RecruitmentDto(Long userId, String title, String content, LocalDateTime startedAt, LocalDateTime expiredAt, String place, int participant, Genre genre) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.place = place;
        this.participant = participant;
        this.genre = genre;
    }

    public static RecruitmentDto from(RecruitmentEntity entity) {
        return new RecruitmentDto(entity.getUserId(), entity.getTitle(), entity.getContent(), entity.getStartedAt(), entity.getExpiredAt(), entity.getPlace(), entity.getParticipant(), entity.getGenre());
    }
}
