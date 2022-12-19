package com.go.conowithme.api.recruitment.service;

import com.go.conowithme.api.recruitment.domain.entity.Genre;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import com.go.conowithme.api.recruitment.domain.repository.RecruitmentRepository;
import com.go.conowithme.api.recruitment.service.dto.InputRecruitmentRequest;
import com.go.conowithme.api.recruitment.service.dto.RecruitmentDto;
import com.go.conowithme.api.recruitment.service.dto.RecruitmentPagingResponse;
import com.go.conowithme.api.recruitment.service.exception.RecruitmentNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecruitmentServiceTest {

    @InjectMocks
    RecruitmentService recruitmentService;

    @Mock
    RecruitmentRepository recruitmentRepository;

    @Test
    void 페이징_조회_성공() {
        //given
        when(recruitmentRepository.findAllByDeletedAtIsNull(any())).thenReturn(new PageImpl<>(List.of(createEntity(1L, "1", 1L), createEntity(2L, "2", 2L))));
        //when
        RecruitmentPagingResponse response = recruitmentService.findAllPaging(0, 10);
        //then
        assertEquals(2, response.getNumberOfElements());
        assertEquals(2, response.getTotalElements());
    }

    @Test
    void 페이징_조회_실패() {
        //given
        when(recruitmentRepository.findAllByDeletedAtIsNull(any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        //when
        RecruitmentPagingResponse response = recruitmentService.findAllPaging(0, 10);
        //then
        assertEquals(0, response.getNumberOfElements());
        assertEquals(0, response.getTotalElements());
    }

    @Test
    void 상세조회_성공() throws RecruitmentNotFoundException {
        //given
        when(recruitmentRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(createEntity(1L, "title", 1L)));
        //when
        RecruitmentDto response = recruitmentService.findById(1L);
        //then
        assertEquals(1L, response.getUserId());
        assertEquals("title", response.getTitle());
    }

    @Test
    void 상세조회_실패() {
        //given
        when(recruitmentRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(RecruitmentNotFoundException.class, () -> recruitmentService.findById(1L));
    }

    @Test
    void 저장_성공() {
        //given
        InputRecruitmentRequest request = InputRecruitmentRequest.of("title", "content", LocalDateTime.now(), LocalDateTime.now(), "place", 1, Genre.BALLAD);
        //when
        recruitmentService.save(request, 1L);
        //then
        verify(recruitmentRepository, times(1)).save(any());
    }

    @Test
    void 삭제_성공() throws RecruitmentNotFoundException {
        //given
        RecruitmentEntity entity = createEntity(1L, "title", 1L);
        when(recruitmentRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(entity));
        //when
        recruitmentService.delete(1L, 1L);
        //then
        assertNotNull(entity.getDeletedAt());
    }

    @Test
    void 본인의_게시글이_아닐_때_삭제_실패() {
        //given
        RecruitmentEntity entity = createEntity(1L, "title", 1L);
        when(recruitmentRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(entity));
        //when
        //then
        assertThrows(IllegalStateException.class, () -> recruitmentService.delete(1L, 2L));
    }

    @Test
    void 수정_성공() throws RecruitmentNotFoundException {
        //given
        InputRecruitmentRequest request = InputRecruitmentRequest.of("title11", "content", LocalDateTime.now(), LocalDateTime.now(), "place", 1, Genre.BALLAD);
        RecruitmentEntity entity = createEntity(1L, "title", 1L);
        when(recruitmentRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(entity));
        when(recruitmentRepository.save(any())).thenReturn(createEntity(1L, "title11", 1L));
        //when
        RecruitmentDto response = recruitmentService.update(1L, request, 1L);
        //then
        assertEquals(request.getTitle(), response.getTitle());
    }

    @Test
    void 본인의_게시글이_아닐_때_수정_불가() {
        //given
        InputRecruitmentRequest request = InputRecruitmentRequest.of("title11", "content", LocalDateTime.now(), LocalDateTime.now(), "place", 1, Genre.BALLAD);
        RecruitmentEntity entity = createEntity(1L, "title", 1L);
        when(recruitmentRepository.findByIdAndDeletedAtIsNull(anyLong())).thenReturn(Optional.of(entity));
        //when
        //then
        assertThrows(IllegalStateException.class, () -> recruitmentService.update(1L, request, 2L));
    }

    private RecruitmentEntity createEntity(long userId, String title, Long id) {
        RecruitmentEntity entity = RecruitmentEntity.of(userId, title, "content", LocalDateTime.now(), LocalDateTime.now().plusHours(3), "place", 1, Genre.BALLAD);
        ReflectionTestUtils.setField(entity, "id", id);
        return entity;
    }
}