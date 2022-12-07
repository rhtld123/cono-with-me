package com.go.conowithme.api.recruitment.domain.repository;

import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Long> {

}