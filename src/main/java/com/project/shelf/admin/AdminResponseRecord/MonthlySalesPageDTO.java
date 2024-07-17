package com.project.shelf.admin.AdminResponseRecord;

import lombok.Builder;
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

            Long cumulativeTotalUsers,
            Long monthlySubUsers,
            Long monthlySales,
            Long cumulativeTotalSales
    ){}

    @Builder
    public record ChartDTO(
            Integer month,
            Long monthlySales
    ){}
}
