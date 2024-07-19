package com.project.shelf.payment.PaymentResponseRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PortOneScheduledDTO(
        @JsonProperty("code")
        Integer code,
        @JsonProperty("message")
        String message,
        @JsonProperty("response")
        List<ResponseDTO> response
) {
    public record ResponseDTO (
            @JsonProperty("customer_uid")
            String customerUid,

            @JsonProperty("merchant_uid")
            String merchantUid,

            @JsonProperty("buyer_email")
            String buyerEmail,

            @JsonProperty("schedule_at")
            Long scheduleAt,

            @JsonProperty("executed_at")
            Long executedAt,

            @JsonProperty("revoked_at")
            Long revokedAt,

            @JsonProperty("amount")
            Integer amount,

            @JsonProperty("currency")
            String currency,

            @JsonProperty("name")
            String name,

            @JsonProperty("schedule_status")
            String scheduleStatus,

            @JsonProperty("payment_status")
            String paymentStatus,

            @JsonProperty("fail_reason")
            String failReason
    ) {}
}
