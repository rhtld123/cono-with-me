package com.go.conowithme.api.recruitment.domain.entity;

import com.go.conowithme.infrastructure.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "recruitment_participations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentParticipationEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "recruitment_id")
    private RecruitmentEntity recruitment;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "comment")
    private String comment;

    private RecruitmentParticipationEntity(Long userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }

    public static RecruitmentParticipationEntity of(Long userId, String comment) {
        return new RecruitmentParticipationEntity(userId, comment);
    }

    public void addRecruitment(RecruitmentEntity recruitment) {
        this.recruitment = recruitment;
    }
}
