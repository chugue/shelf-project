package com.project.shelf.payment.PaymentResponseRecord;

import lombok.Builder;

@Builder
public record MonthlySaleDTO (
        Integer year,
        Integer month,
        Long totalSales,
        Long subUser
){}