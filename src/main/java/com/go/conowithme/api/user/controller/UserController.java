package com.go.conowithme.api.user.controller;

import com.go.conowithme.api.user.controller.dto.request.UserLoginRequest;
import com.go.conowithme.api.user.controller.dto.request.UserSignupRequest;
import com.go.conowithme.api.user.service.UserLoginService;
import com.go.conowithme.api.user.service.UserService;
import com.go.conowithme.api.user.service.dto.UserLoginDto;
import com.go.conowithme.api.user.service.dto.UserSignupDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserLoginService userLoginService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<?> signup(@RequestBody @Valid UserSignupRequest request) {
        userService.signup(UserSignupDto.of(request.email(),request.password(), request.name(), request.nickname()));
        return ResponseEntity.ok()
            .body(null);
    }

    @PostMapping("/api/v1/users/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest request) {
        return ResponseEntity.ok(userLoginService.login(UserLoginDto.of(request.email(),request.password())));
    }
}
