package com.go.conowithme.api.user.service;

import com.go.conowithme.api.user.domain.entity.UserEntity;
import com.go.conowithme.api.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

}
