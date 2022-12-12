package com.go.conowithme.api.recruitment.domain.repository;

import com.go.conowithme.api.recruitment.domain.entity.Genre;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class RecruitmentRepositoryTest {

    @Autowired
    RecruitmentRepository recruitmentRepository;

    @BeforeEach
    void setUp() {
        IntStream.range(0, 15).forEach(i -> recruitmentRepository.save(RecruitmentEntity.of(Long.valueOf(i + 1), "title" + i, "content", LocalDateTime.now(), LocalDateTime.now(), "place", i + 1, Genre.BALLAD)));
    }

    @AfterEach
    void tearDown() {
        recruitmentRepository.deleteAll();
    }

    @Test
    void 페이징_조회_성공() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        //when
        Page<RecruitmentEntity> all = recruitmentRepository.findAllByDeletedAtIsNull(pageable);
        //then
        assertEquals(10, all.getNumberOfElements());
        assertEquals(15, all.getTotalElements());

    }

    @Test
    void 페이징_조회_값_없음() {
        //given
        Pageable pageable = PageRequest.of(2, 10);
        //when
        Page<RecruitmentEntity> all = recruitmentRepository.findAllByDeletedAtIsNull(pageable);
        //then
        assertEquals(0, all.getNumberOfElements());
        assertEquals(15, all.getTotalElements());
    }
}