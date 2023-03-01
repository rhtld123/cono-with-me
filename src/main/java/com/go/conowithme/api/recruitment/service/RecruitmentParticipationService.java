package com.go.conowithme.api.recruitment.service;

import com.go.conowithme.api.recruitment.controller.dto.request.RecruitmentParticipationRequest;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentParticipationEntity;
import com.go.conowithme.api.recruitment.domain.repository.RecruitmentParticipationRepository;
import com.go.conowithme.api.recruitment.domain.repository.RecruitmentRepository;
import com.go.conowithme.api.recruitment.service.dto.RecruitmentParticipationDto;
import com.go.conowithme.api.recruitment.service.dto.RecruitmentParticipationSliceResponse;
import com.go.conowithme.api.recruitment.service.exception.RecruitmentNotFoundException;
import com.go.conowithme.api.recruitment.service.exception.RecruitmentParticipationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentParticipationService {

    private final RecruitmentParticipationRepository recruitmentParticipationRepository;
    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentParticipationDto findByRecruitmentIdAndUserId(Long recruitmentId, Long userId) throws RecruitmentNotFoundException, RecruitmentParticipationNotFoundException {
        RecruitmentEntity recruitmentEntity = findRecruitment(recruitmentId);
        RecruitmentParticipationEntity recruitmentParticipationEntity = findByRecruitmentAndUserIdAndDeletedAtIsNull(recruitmentEntity, userId);
        return RecruitmentParticipationDto.of(recruitmentId, recruitmentParticipationEntity.getId(), recruitmentParticipationEntity.getComment(), recruitmentParticipationEntity.getCreatedAt());
    }

    @Transactional
    public void save(Long recruitmentId, RecruitmentParticipationRequest request, Long userId) throws RecruitmentNotFoundException {
        RecruitmentEntity recruitmentEntity = findRecruitment(recruitmentId);
        RecruitmentParticipationEntity recruitmentParticipationEntity = RecruitmentParticipationEntity.of(userId, request.getComment());
        recruitmentEntity.addParticipation(recruitmentParticipationEntity);
    }

    @Transactional
    public void delete(Long recruitmentId, Long userId) throws RecruitmentNotFoundException, RecruitmentParticipationNotFoundException {
        RecruitmentEntity recruitmentEntity = findRecruitment(recruitmentId);
        RecruitmentParticipationEntity recruitmentParticipationEntity = findByRecruitmentAndUserIdAndDeletedAtIsNull(recruitmentEntity, userId);
        recruitmentEntity.deleteParticipation(recruitmentParticipationEntity);
    }

    public RecruitmentParticipationSliceResponse findAllByRecruitmentId(Long recruitmentId, int page, int size) {
        Slice<RecruitmentParticipationEntity> result = recruitmentParticipationRepository.findByRecruitmentIdAndDeletedAtIsNull(recruitmentId, PageRequest.of(page, size));
        return RecruitmentParticipationSliceResponse.of(result.isFirst(), result.isLast(), result.hasNext(), convertToDto(result.getContent()));
    }

    private RecruitmentParticipationEntity findByRecruitmentAndUserIdAndDeletedAtIsNull(RecruitmentEntity recruitmentEntity, Long userId) throws RecruitmentParticipationNotFoundException {
        return recruitmentParticipationRepository.findByRecruitmentAndUserIdAndDeletedAtIsNull(recruitmentEntity, userId)
                .orElseThrow(RecruitmentParticipationNotFoundException::thrown);
    }

    private RecruitmentEntity findRecruitment(Long recruitmentId) throws RecruitmentNotFoundException {
        return recruitmentRepository.findByIdAndDeletedAtIsNull(recruitmentId)
                .orElseThrow(RecruitmentNotFoundException::thrown);
    }

    private List<RecruitmentParticipationDto> convertToDto(List<RecruitmentParticipationEntity> entities) {
        return entities.stream()
                .map(data -> RecruitmentParticipationDto.of(data.getRecruitment().getId(), data.getId(), data.getComment(), data.getCreatedAt()))
                .collect(Collectors.toList());
    }
}

