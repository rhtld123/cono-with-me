package com.go.conowithme.api.user.service;

import com.go.conowithme.api.user.domain.entity.UserEntity;
import com.go.conowithme.api.user.domain.repository.UserRepository;
import com.go.conowithme.api.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;

    public UserEntity findByEmailAndDeletedAtIsNull(String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email)
            .orElseThrow(()-> UserNotFoundException.thrown("email", email));
    }
}
