package com.go.conowithme.api.user.controller;

import com.go.conowithme.api.user.controller.dto.request.RefreshTokenRequest;
import com.go.conowithme.api.user.controller.dto.request.UserLoginRequest;
import com.go.conowithme.api.user.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest request) {
        return ResponseEntity.ok(authService.login(request.email(),request.password()));
    }

    @PostMapping("/api/v1/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refresh(request.accessToken(),request.refreshToken()));
    }
}
