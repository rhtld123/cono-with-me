package com.go.conowithme.api.recruitment.service;

import com.go.conowithme.api.recruitment.controller.dto.request.RecruitmentRequest;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import com.go.conowithme.api.recruitment.domain.repository.RecruitmentRepository;
import com.go.conowithme.api.recruitment.service.exception.RecruitmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public void save(RecruitmentRequest request, Long userId) {
        recruitmentRepository.save(request.toEntity(userId));
    }

    @Transactional
    public void delete(Long recruitmentId, Long userId) throws RecruitmentNotFoundException {
        RecruitmentEntity recruitment = getRecruitment(recruitmentId);
        validateRecruitmentOwner(recruitment, userId);
        recruitment.delete();
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
