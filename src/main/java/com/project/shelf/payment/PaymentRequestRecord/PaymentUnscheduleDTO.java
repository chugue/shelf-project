package com.project.shelf.payment.PaymentRequestRecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentUnscheduleDTO(
        @JsonProperty("customer_uid")
        String customerUid, // 구매자의 결제 수단 식별 고유번호 (ex.customer_1720595101182)
        @JsonProperty("user_id")
        Integer userId // 구매자 id(pk)
) {}
