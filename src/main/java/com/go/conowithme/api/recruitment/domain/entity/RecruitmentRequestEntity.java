package com.go.conowithme.api.recruitment.domain.entity;

import com.go.conowithme.infrastructure.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "recruitment_requests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentRequestEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "recruitment_id")
    private RecruitmentEntity recruitment;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "comment")
    private String comment;

    private RecruitmentRequestEntity(Long userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }

    public static RecruitmentRequestEntity of(Long userId, String comment) {
        return new RecruitmentRequestEntity(userId, comment);
    }

    public void addRecruitment(RecruitmentEntity recruitment) {
        this.recruitment = recruitment;
    }
}
