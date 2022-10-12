package com.go.conowithme.api.user.domain.repository;

import com.go.conowithme.api.user.domain.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailAndDeletedAtIsNull(String email);
}
