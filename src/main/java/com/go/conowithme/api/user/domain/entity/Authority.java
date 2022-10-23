package com.go.conowithme.api.user.domain.entity;

import lombok.Getter;

@Getter
public enum Authority {
    ROLE_USER("일반회원"),
    ROLE_ADMIN("관리자");

    private String description;

    Authority(String description) {
        this.description = description;
    }
}
