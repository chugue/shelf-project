package com.project.shelf.user.UserResponseRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record NaverRespDTO(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") String expiresIn,
        NaverUserDTO naverUserDTO
) {
    @Builder
    public record NaverUserDTO(
            @JsonProperty("response") Response response
    ) {

        @Builder
        public record Response(
                String id,
                String email,
                String name
        ) {
        }
    }
}
