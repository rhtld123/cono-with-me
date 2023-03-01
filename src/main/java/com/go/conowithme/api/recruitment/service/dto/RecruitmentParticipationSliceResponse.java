package com.go.conowithme.api.recruitment.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentParticipationSliceResponse {
    @JsonProperty("first")
    private boolean isFirst;
    @JsonProperty("last")
    private boolean isLast;
    @JsonProperty("next")
    private boolean hasNext;
    private List<RecruitmentParticipationDto> contents;

    private RecruitmentParticipationSliceResponse(boolean isFirst, boolean isLast, boolean hasNext, List<RecruitmentParticipationDto> contents) {
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.hasNext = hasNext;
        this.contents = contents;
    }

    public static RecruitmentParticipationSliceResponse of(boolean isFirst, boolean isLast, boolean hasNext, List<RecruitmentParticipationDto> contents) {
        return new RecruitmentParticipationSliceResponse(isFirst, isLast, hasNext, contents);
    }
}
