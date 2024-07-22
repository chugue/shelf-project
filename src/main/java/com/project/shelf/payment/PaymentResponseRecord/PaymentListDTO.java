package com.project.shelf.payment.PaymentResponseRecord;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PaymentListDTO(
        Integer paymentListCount,
        List<PaymentDTO> paymentDTOList
) {
    @Builder
    public record PaymentDTO(
            Integer paymentId,
            String subTypePeriod,
            String paymentStatus,
            LocalDate paymentAt,
            String amount
    ) {}
}
