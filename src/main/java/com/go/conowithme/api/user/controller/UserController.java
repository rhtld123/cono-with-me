package com.go.conowithme.api.user.controller;

import com.go.conowithme.api.user.controller.dto.request.UserSignupRequest;
import com.go.conowithme.api.user.service.UserService;
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

    @PostMapping("/api/v1/users")
    public ResponseEntity<?> signup(@RequestBody @Valid UserSignupRequest request) {
        userService.signup(UserSignupDto.of(request.email(),request.password(), request.name(), request.nickname()));
        return ResponseEntity.ok()
            .body(null);
    }
}
