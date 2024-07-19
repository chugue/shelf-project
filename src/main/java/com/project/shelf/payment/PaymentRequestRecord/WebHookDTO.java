package com.project.shelf.payment.PaymentRequestRecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebHookDTO(
        @JsonProperty("imp_uid")
        String impUid,

        @JsonProperty("status")
        String status,

        @JsonProperty("error_code")
        String errorCode,

        @JsonProperty("error_msg")
        String errorMsg
) {}