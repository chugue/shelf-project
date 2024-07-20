package com.project.shelf.payment.PaymentResponseRecord;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PortOneUnscheduledDTO(
        @JsonProperty("code") // 이렇게 해주면 access_token으로 파싱된 값이 accessToken에 들어간다.(jackson 라이브러리)
        Integer code,

        @JsonProperty("message")
        String message,

        @JsonProperty("response")
        List<ResponseDTO> response
) {
    public record ResponseDTO(
            @JsonProperty("customer_uid")
            String customerUid,
            @JsonProperty("merchant_uid")
            String merchantUid,
            @JsonProperty("imp_uid")
            String impUid,
            @JsonProperty("schedule_at")
            Long scheduleAt,
            @JsonProperty("revoked_at")
            Long revokedAt, // 결제 실행 철회 시각
            @JsonProperty("amount")
            Integer amount,
            @JsonProperty("currency")
            String currency,
            @JsonProperty("name")
            String name,
            @JsonProperty("schedule_status")
            String scheduleStatus // 예약 상태
    ) {}
}
