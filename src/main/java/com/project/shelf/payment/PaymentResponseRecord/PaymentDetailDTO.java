package com.project.shelf.payment.PaymentResponseRecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentDetailDTO(
        @JsonProperty("code")
        Integer code,
        @JsonProperty("message")
        String message,
        @JsonProperty("response")
        ResponseDTO response
) {
    public record ResponseDTO (
            @JsonProperty("amount")
            Integer amount,
            @JsonProperty("card_name")
            String cardName,
            @JsonProperty("card_number")
            String cardNumber,
            @JsonProperty("imp_uid")
            String impUid,
            @JsonProperty("merchant_uid")
            String merchantUid,
            @JsonProperty("name")
            String name,
            @JsonProperty("status")
            String status, // 결제 상태
            @JsonProperty("paid_at")
            String paidAt, // 결제 시각(결제완료가 안 된 경우 0으로 표시)
            @JsonProperty("buyer_email")
            String buyerEmail, // 구매자 email 주소
            @JsonProperty("pay_method")
            String payMethod, // 결제 수단 구분 코드
            @JsonProperty("customer_uid")
            String customerUid // 결제 수단 구분 코드
    ) {}
}