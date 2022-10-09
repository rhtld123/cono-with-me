package com.go.conowithme.modules.user.adapter.out.persistence.entity;

import com.go.conowithme.infrastructure.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "users",
    indexes = {
        @Index(name = "index_users_created_at", columnList = "created_at"),
        @Index(name = "index_users_updated_at", columnList = "updated_at"),
        @Index(name = "index_users_deleted_at", columnList = "deleted_at")
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "password", length = 255)
    private String password;
    @Column(name = "name", length = 100)
    private String name;

}
