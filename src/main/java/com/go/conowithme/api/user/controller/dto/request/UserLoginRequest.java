package com.go.conowithme.api.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;

public record UserLoginRequest(
    @Schema(title = "Email")
    @NotEmpty(message = "E-Mail은 필수입니다.")
    String email,
    @Schema(title = "비밀번호")
    @NotEmpty(message = "비밀번호는 필수입니다.")
    String password
) {

}
