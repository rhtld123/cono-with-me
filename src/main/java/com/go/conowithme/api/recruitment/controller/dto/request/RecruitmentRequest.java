package com.go.conowithme.api.recruitment.controller.dto.request;

import com.go.conowithme.api.recruitment.domain.entity.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentRequest {
    @Schema(description = "제목")
    @NotEmpty
    private String title;
    @Schema(description = "내용")
    @NotEmpty
    private String content;
    @Schema(description = "일정 시작 일자")
    @NotNull
    private LocalDateTime startedAt;
    @Schema(description = "모집 마감 일자")
    @NotNull
    private LocalDateTime expiredAt;
    @Schema(description = "장소")
    @NotEmpty
    private String place;
    @Schema(description = "인원 수")
    @Min(1)
    private int participant;
    @Schema(description = "선호 장르")
    @NotNull
    private Genre genre;

    private RecruitmentRequest(String title, String content, LocalDateTime startedAt, LocalDateTime expiredAt, String place, int participant, Genre genre) {
        this.title = title;
        this.content = content;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.place = place;
        this.participant = participant;
        this.genre = genre;
    }

    public static RecruitmentRequest of(String title, String content, LocalDateTime startedAt, LocalDateTime expiredAt, String place, int participant, Genre genre) {
        return new RecruitmentRequest(title, content, startedAt, expiredAt, place, participant, genre);
    }
}
