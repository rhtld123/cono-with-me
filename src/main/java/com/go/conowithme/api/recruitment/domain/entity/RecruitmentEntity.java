package com.go.conowithme.api.recruitment.domain.entity;

import com.go.conowithme.api.recruitment.service.dto.InputRecruitmentRequest;
import com.go.conowithme.infrastructure.common.entity.BaseEntity;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "recruitments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "participant")
    private int participant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitmentRequestEntity> requests = new ArrayList<>();

    private RecruitmentEntity(Long userId, String title, String content,
        LocalDateTime startedAt, LocalDateTime expiredAt, String place, int participant,
        Genre genre) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.place = place;
        this.participant = participant;
        this.genre = genre;
    }

    public static RecruitmentEntity of(Long userId, String title, String content,
        LocalDateTime startedAt, LocalDateTime expiredAt, String place, int participant,
        Genre genre) {
        return new RecruitmentEntity(userId, title, content, startedAt, expiredAt, place,
            participant, genre);
    }

    public void addRequest(RecruitmentRequestEntity requestEntity) {
        requestEntity.addRecruitment(this);
        this.requests.add(requestEntity);
    }

    public void update(InputRecruitmentRequest request, Long userId) {
        Assert.notNull(request, "요청 항목이 비어있어 수정할 수 없습니다.");
        this.title = request.getTitle();
        this.content = request.getContent();
        this.startedAt = request.getStartedAt();
        this.expiredAt = request.getExpiredAt();
        this.place = request.getPlace();
        this.participant = request.getParticipant();
        this.genre = request.getGenre();
        this.userId = userId;
    }
}
