package com.go.conowithme.api.user.domain.entity;

import com.go.conowithme.infrastructure.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
    name = "users",
    indexes = {
        @Index(name = "index_users_email", columnList = "email"),
        @Index(name = "index_users_nickname", columnList = "nickname"),
        @Index(name = "index_users_created_at", columnList = "created_at"),
        @Index(name = "index_users_updated_at", columnList = "updated_at"),
        @Index(name = "index_users_deleted_at", columnList = "deleted_at")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_users_email", columnNames = "email"),
        @UniqueConstraint(name = "unique_users_nickname", columnNames = "nickname")
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Column(name = "email", length = 100)
    private String email;
    @Convert(converter = PasswordConverter.class)
    @Column(name = "password", length = 255)
    private String password;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "nickname", length = 100)
    private String nickname;
    @Column
    @Enumerated(EnumType.STRING)
    private Authority authority;


    private UserEntity(String email, String password, String name, String nickname,
        Authority authority) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.authority = authority;
    }

    public static UserEntity of(String email, String password, String name, String nickname,
        Authority authority) {
        return new UserEntity(email, password, name, nickname, authority);
    }
}
