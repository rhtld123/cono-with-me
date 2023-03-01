package com.go.conowithme.api.recruitment.domain.repository;

import com.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer;
import com.go.conowithme.api.recruitment.domain.entity.Genre;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentParticipationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
public class RecruitmentParticipationRepositoryTest {

    @Autowired
    RecruitmentRepository recruitmentRepository;
    @Autowired
    RecruitmentParticipationRepository recruitmentParticipationRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void slice_조회_테스트() {
        //given
        RecruitmentEntity recruitmentEntity = RecruitmentEntity.of(1L, "title", "content", LocalDateTime.now(), LocalDateTime.now(), "place", 1, Genre.BALLAD);
        RecruitmentParticipationEntity entity1 = RecruitmentParticipationEntity.of(1L, "test");
        RecruitmentParticipationEntity entity2 = RecruitmentParticipationEntity.of(2L, "test");
        RecruitmentParticipationEntity entity3 = RecruitmentParticipationEntity.of(3L, "test");
        RecruitmentParticipationEntity entity4 = RecruitmentParticipationEntity.of(4L, "test");
        recruitmentEntity.addParticipation(entity1);
        recruitmentEntity.addParticipation(entity2);
        recruitmentEntity.addParticipation(entity3);
        recruitmentEntity.addParticipation(entity4);
        recruitmentRepository.save(recruitmentEntity);
        entityManager.clear();
        //when
        Slice<RecruitmentParticipationEntity> byRecruitmentIdAndDeletedAtIsNull = recruitmentParticipationRepository.findByRecruitmentIdAndDeletedAtIsNull(1L, PageRequest.of(1, 2));
        //then
    }
}
