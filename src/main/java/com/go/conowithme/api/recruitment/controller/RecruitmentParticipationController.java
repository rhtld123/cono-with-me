package com.go.conowithme.api.recruitment.controller;

import com.go.conowithme.api.recruitment.controller.dto.request.RecruitmentParticipationRequest;
import com.go.conowithme.api.recruitment.service.RecruitmentParticipationService;
import com.go.conowithme.api.recruitment.service.dto.RecruitmentParticipationDto;
import com.go.conowithme.api.recruitment.service.dto.RecruitmentParticipationSliceResponse;
import com.go.conowithme.api.recruitment.service.exception.RecruitmentNotFoundException;
import com.go.conowithme.api.recruitment.service.exception.RecruitmentParticipationNotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Recruitment Participation")
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class RecruitmentParticipationController {

    private final RecruitmentParticipationService recruitmentParticipationService;

    @GetMapping("/api/v1/recruitment/{recruitmentId}/request")
    public ResponseEntity<RecruitmentParticipationDto> findByRecruitmentIdAndUserId(@PathVariable("recruitmentId") Long recruitmentId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) throws RecruitmentNotFoundException, RecruitmentParticipationNotFoundException {
        return ResponseEntity.ok(recruitmentParticipationService.findByRecruitmentIdAndUserId(recruitmentId, Long.valueOf(user.getUsername())));
    }

    @PostMapping("/api/v1/recruitment/{recruitmentId}/requests")
    public ResponseEntity<Void> addRequest(@PathVariable("recruitmentId") Long recruitmentId, @RequestBody RecruitmentParticipationRequest request, @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) throws RecruitmentNotFoundException {
        recruitmentParticipationService.save(recruitmentId, request, Long.valueOf(user.getUsername()));
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/api/v1/recruitment/{recruitmentId}/request")
    public ResponseEntity<Void> deleteRequest(@PathVariable("recruitmentId") Long recruitmentId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) throws RecruitmentNotFoundException, RecruitmentParticipationNotFoundException {
        recruitmentParticipationService.delete(recruitmentId, Long.valueOf(user.getUsername()));
        return ResponseEntity.ok(null);
    }

    @GetMapping("/api/v1/recruitment/{recruitmentId}/requests")
    public ResponseEntity<RecruitmentParticipationSliceResponse> findAllByRecruitmentId(@PathVariable("recruitmentId") Long recruitmentId, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return ResponseEntity.ok(recruitmentParticipationService.findAllByRecruitmentId(recruitmentId, page, size));
    }
}
