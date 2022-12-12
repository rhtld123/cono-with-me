package com.go.conowithme.api.recruitment.service;

import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import com.go.conowithme.api.recruitment.domain.repository.RecruitmentRepository;
import com.go.conowithme.api.recruitment.service.dto.InputRecruitmentRequest;
import com.go.conowithme.api.recruitment.service.dto.RecruitmentDto;
import com.go.conowithme.api.recruitment.service.dto.RecruitmentPagingResponse;
import com.go.conowithme.api.recruitment.service.exception.RecruitmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentPagingResponse findAllPaging(int page, int size) {
        Page<RecruitmentEntity> entities = findAll(page, size);
        List<RecruitmentDto> contents = entities.stream()
                .map(RecruitmentDto::from)
                .collect(Collectors.toList());
        return RecruitmentPagingResponse.of(contents, entities.getNumberOfElements(), entities.getTotalElements());
    }

    public RecruitmentDto findById(Long recruitmentId) throws RecruitmentNotFoundException {
        return RecruitmentDto.from(getRecruitment(recruitmentId));
    }

    @Transactional
    public void save(InputRecruitmentRequest request, Long userId) {
        recruitmentRepository.save(request.toEntity(userId));
    }

    @Transactional
    public void delete(Long recruitmentId, Long userId) throws RecruitmentNotFoundException {
        RecruitmentEntity recruitment = getRecruitment(recruitmentId);
        validateRecruitmentOwner(recruitment, userId);
        recruitment.delete();
    }

    @Transactional
    public RecruitmentDto update(Long recruitmentId, InputRecruitmentRequest request, Long userId) throws RecruitmentNotFoundException {
        RecruitmentEntity recruitmentEntity = getRecruitment(recruitmentId);
        recruitmentEntity.update(request, userId);
        return RecruitmentDto.from(recruitmentRepository.save(recruitmentEntity));
    }

    private Page<RecruitmentEntity> findAll(int page, int size) {
        return recruitmentRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
    }

    private RecruitmentEntity getRecruitment(Long recruitmentId) throws RecruitmentNotFoundException {
        return recruitmentRepository.findById(recruitmentId).orElseThrow(RecruitmentNotFoundException::thrown);
    }

    private void validateRecruitmentOwner(RecruitmentEntity recruitment, Long userId) {
        if (recruitment.getUserId() != userId) {
            throw new IllegalStateException("해당 공고의 작성자가 아닙니다.");
        }
    }
}
