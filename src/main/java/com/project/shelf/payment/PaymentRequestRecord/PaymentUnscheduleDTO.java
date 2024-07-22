package com.project.shelf.payment.PaymentRequestRecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentUnscheduleDTO(

        @JsonProperty("user_id")
        Integer userId // 구매자 id(pk)
) {}
