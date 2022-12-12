package com.go.conowithme.api.recruitment.service.dto;

import com.go.conowithme.api.recruitment.domain.entity.Genre;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class InputRecruitmentRequest {

    private String title;
    private String content;
    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;
    private String place;
    private int participant;
    private Genre genre;

    private InputRecruitmentRequest(String title, String content, LocalDateTime startedAt, LocalDateTime expiredAt, String place, int participant, Genre genre) {
        this.title = title;
        this.content = content;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.place = place;
        this.participant = participant;
        this.genre = genre;
    }

    public static InputRecruitmentRequest of(String title, String content, LocalDateTime startedAt, LocalDateTime expiredAt, String place, int participant, Genre genre) {
        return new InputRecruitmentRequest(title, content, startedAt, expiredAt, place, participant, genre);
    }

    public RecruitmentEntity toEntity(Long userId) {
        return RecruitmentEntity.of(userId, title, content, startedAt, expiredAt, place, participant, genre);
    }
}
