package com.project.shelf.payment.PaymentRequestRecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentSaveReqDTO(
        @JsonProperty("imp_uid")
        String impUid,
        @JsonProperty("pay_method")
        String payMethod,
        @JsonProperty("merchant_uid")
        String merchantUid,
        @JsonProperty("name")
        String name,
        @JsonProperty("paid_amount")
        Integer paidAmount,
        @JsonProperty("status")
        String status,
        @JsonProperty("paid_at")
        String paidAt,
        @JsonProperty("card_name")
        String cardName,
        @JsonProperty("card_number")
        String cardNumber,
        @JsonProperty("customer_uid")
        String customerUid,
        @JsonProperty("buyer_email")
        String buyerEmail
) {}
