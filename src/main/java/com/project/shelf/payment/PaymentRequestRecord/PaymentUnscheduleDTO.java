package com.project.shelf.payment.PaymentRequestRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PaymentUnscheduleDTO(
        @JsonProperty("user_id")
        Integer userId // 구매자 id(pk)
) {}
