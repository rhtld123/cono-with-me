package com.go.conowithme.api.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;

public record RefreshTokenRequest(
    @Schema(title = "accessToken")
    @NotEmpty(message = "AccessToken은 필수입니다.")
    String accessToken,
    @Schema(title = "RefreshToken")
    @NotEmpty(message = "RefreshToken은 필수입니다.")
    String refreshToken) {
}
