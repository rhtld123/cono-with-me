package com.go.conowithme.api.user.controller;

import com.go.conowithme.api.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void signup() {
        //given
        //when
        //then
    }

    @Test
    void login() {
        //given
        //when
        //then
    }
}