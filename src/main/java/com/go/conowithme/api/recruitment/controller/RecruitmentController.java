package com.go.conowithme.api.recruitment.controller;

import com.go.conowithme.api.recruitment.controller.dto.request.RecruitmentRequest;
import com.go.conowithme.api.recruitment.service.RecruitmentService;
import com.go.conowithme.api.recruitment.service.dto.RecruitmentDto;
import com.go.conowithme.api.recruitment.service.exception.RecruitmentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Recruitment")
@PreAuthorize("hasRole('ROLE_USER')")
@RestController
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @Operation(summary = "모집공고 전체 조회", description = "모집공고를 전체 조회합니다.")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공",
                            content = @Content(schema = @Schema(implementation = RecruitmentDto.class))
                    )
            }
    )
    @GetMapping("/api/v1/recruitments")
    public ResponseEntity<?> findAll(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return null;
    }

    @Operation(summary = "모집공고 상세 조회", description = "모집공고를 상세조회 합니다.")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공",
                            content = @Content(schema = @Schema(implementation = RecruitmentDto.class))
                    )
            }
    )
    @GetMapping("/api/v1/recruitments/{recruitmentId}")
    public ResponseEntity<?> findByRecruitmentId(@PathVariable(name = "recruitmentId") Long recruitmentId) {
        return null;
    }

    @Operation(summary = "모집공고 등록", description = "모집공고를 등록합니다.")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공"
                    )
            }
    )
    @PostMapping("/api/v1/recruitments")
    public ResponseEntity<?> addRecruitment(@Valid @RequestBody RecruitmentRequest request, @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {
        recruitmentService.save(request, Long.valueOf(user.getUsername()));
        return ResponseEntity.ok()
                .body(null);
    }

    @Operation(summary = "모집공고 수정", description = "모집 공고를 수정합니다.")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공"
                    )
            }
    )
    @PutMapping("/api/v1/recruitments/{recruitmentId}")
    public ResponseEntity<?> updateRecruitment(@PathVariable(name = "recruitmentId") Long recruitmentId, @Valid @RequestBody RecruitmentRequest request) {
        return null;
    }

    @Operation(summary = "모집공고 삭제", description = "모집공고를 삭제합니다.")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청 성공"
                    )
            }
    )
    @DeleteMapping("/api/v1/recruitments/{recruitmentId}")
    public ResponseEntity<?> deleteRecruitment(@PathVariable(name = "recruitmentId") Long recruitmentId, @AuthenticationPrincipal UserDetails user) throws RecruitmentNotFoundException {
        recruitmentService.delete(recruitmentId, Long.valueOf(user.getUsername()));
        return ResponseEntity.ok().body(null);
    }
}
