package com.go.conowithme.api.recruitment.domain.repository;

import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentParticipationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitmentParticipationRepository extends JpaRepository<RecruitmentParticipationEntity, Long> {

    Optional<RecruitmentParticipationEntity> findByRecruitmentAndUserIdAndDeletedAtIsNull(RecruitmentEntity recruitment, Long userId);

    Slice<RecruitmentParticipationEntity> findByRecruitmentIdAndDeletedAtIsNull(Long recruitmentId, Pageable pageable);
}
