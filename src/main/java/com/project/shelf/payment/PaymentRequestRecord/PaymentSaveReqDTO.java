package com.project.shelf.payment.PaymentRequestRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shelf.payment.Payment;

public record PaymentSaveReqDTO(
        @JsonProperty("imp_uid")
        String impUid,
        @JsonProperty("pay_method")
        String payMethod,
        @JsonProperty("merchant_uid")
        String merchantUid,
        @JsonProperty("name")
        String name,
        @JsonProperty("amount")
        Integer amount,
        @JsonProperty("status")
        String status,
        @JsonProperty("order_date")
        String orderDate,
        @JsonProperty("card_name")
        String cardName,
        @JsonProperty("customer_uid")
        String customerUid,
        @JsonProperty("buyer_email")
        String buyerEmail
) {}
