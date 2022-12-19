package com.go.conowithme.api.recruitment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.go.conowithme.api.recruitment.controller.dto.request.RecruitmentRequest;
import com.go.conowithme.api.recruitment.domain.entity.Genre;
import com.go.conowithme.api.recruitment.domain.entity.RecruitmentEntity;
import com.go.conowithme.api.recruitment.domain.repository.RecruitmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class RecruitmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        recruitmentRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    void 공고_등록() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recruitments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RecruitmentRequest.of("title", "content", LocalDateTime.now(), LocalDateTime.now(), "place", 1, Genre.BALLAD))))
                .andDo(print())
                //then
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    void 공고_조회_페이징() throws Exception {
        //given
        IntStream.range(0, 20).forEach(i -> recruitmentRepository.save(createEntity(1L, "title" + i)));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recruitments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(10))
                .andExpect(jsonPath("$.totalElements").value(20));
    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    void 공고_수정() throws Exception {
        //given
        RecruitmentEntity recruitment = recruitmentRepository.save(createEntity(1L, "title1"));
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/recruitments/{recruitmentId}", recruitment.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RecruitmentRequest.of("title2", "content", LocalDateTime.now(), LocalDateTime.now(), "place", 1, Genre.BALLAD))))
                .andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title2"));
    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    void 작성자가_아닐_때_수정_시_오류() throws Exception {
        //given
        RecruitmentEntity recruitment = recruitmentRepository.save(createEntity(2L, "title1"));
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/recruitments/{recruitmentId}", recruitment.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RecruitmentRequest.of("title2", "content", LocalDateTime.now(), LocalDateTime.now(), "place", 1, Genre.BALLAD))))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    void 삭제_성공() throws Exception {
        //given
        RecruitmentEntity recruitment = recruitmentRepository.save(createEntity(1L, "title1"));
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/recruitments/{recruitmentId}", recruitment.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    void 작성자가_아닐_때_삭제_시_오류() throws Exception {
        //given
        RecruitmentEntity recruitment = recruitmentRepository.save(createEntity(2L, "title1"));
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/recruitments/{recruitmentId}", recruitment.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    void 상세_조회() throws Exception {
        //given
        RecruitmentEntity recruitment = recruitmentRepository.save(createEntity(1L, "title1"));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recruitments/{recruitmentId}", recruitment.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(recruitment.getTitle()))
                .andExpect(jsonPath("$.userId").value(recruitment.getUserId()))
                .andExpect(jsonPath("$.content").value(recruitment.getContent()))
                .andExpect(jsonPath("$.startedAt").value(recruitment.getStartedAt().toString()))
                .andExpect(jsonPath("$.expiredAt").value(recruitment.getExpiredAt().toString()))
                .andExpect(jsonPath("$.place").value(recruitment.getPlace()))
                .andExpect(jsonPath("$.participant").value(recruitment.getParticipant()))
                .andExpect(jsonPath("$.genre").value(recruitment.getGenre().toString()));

    }

    @Test
    @WithMockUser(username = "1", roles = {"USER"})
    void 상세_조회_시_해당_ID의_게시글이_없을_때() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recruitments/{recruitmentId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest());
    }

    private RecruitmentEntity createEntity(long userId, String title) {
        return RecruitmentEntity.of(userId, title, "content", LocalDateTime.now(), LocalDateTime.now().plusHours(3), "place", 1, Genre.BALLAD);
    }
}