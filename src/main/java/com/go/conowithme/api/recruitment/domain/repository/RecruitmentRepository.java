package com.go.conowithme.api.recruitment.domain.repository;

import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Long> {

    Page<RecruitmentEntity> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<RecruitmentEntity> findByIdAndDeletedAtIsNull(Long id);
}
