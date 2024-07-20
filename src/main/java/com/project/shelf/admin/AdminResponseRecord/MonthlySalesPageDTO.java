package com.project.shelf.admin.AdminResponseRecord;

import lombok.Builder;

import java.text.NumberFormat;
import java.util.List;

@Builder
public record MonthlySalesPageDTO(
        List<ListDTO> salesList,
        List<ChartDTO> chartData
){
    @Builder
    public record ListDTO(
            Integer year,
            Integer month,

            Long monthlySubUsers,       // 한 달 구독자
            Long cumulativeTotalUsers,  // 누적 사용자
//            Long monthlySales,
            String monthlySales,  // 한 달 매출
//            Long cumulativeTotalSales,
            String cumulativeTotalSales
                                        // 누적 매출

    ){}

    @Builder
    public record ChartDTO(
            Integer month,
            Long monthlySales
    ){}
}
