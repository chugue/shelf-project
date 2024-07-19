package com.project.shelf.payment.PaymentResponseRecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PortOneTokenDTO(
        @JsonProperty("code")
        Integer code,
        @JsonProperty("message")
        String message,
        @JsonProperty("response")
        ResponseDTO response
) {
    public record ResponseDTO(
       @JsonProperty("access_token")
       String accessToken,
       @JsonProperty("now")
       Long now,
       @JsonProperty("expired_at")
       Long expiredAt
    ) {}
}
