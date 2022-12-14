package com.go.conowithme.api.recruitment.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecruitmentPagingResponse {
    @Schema(description = "조회 결과 상세 내역")
    private List<RecruitmentDto> recruitmentDto;
    @Schema(description = "현재 페이지의 항목 개수")
    private long numberOfElements;
    @Schema(description = "모든 페이지의 수")
    private long totalElements;

    private RecruitmentPagingResponse(List<RecruitmentDto> recruitmentDto, long numberOfElements, long totalElements) {
        this.recruitmentDto = recruitmentDto;
        this.numberOfElements = numberOfElements;
        this.totalElements = totalElements;
    }

    public static RecruitmentPagingResponse of(List<RecruitmentDto> recruitmentDto, long currentPage, long totalElements) {
        return new RecruitmentPagingResponse(recruitmentDto, currentPage, totalElements);
    }
}
