package com.project.shelf.user.UserResponseRecord;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MainDTO(
        List<BestSellerDTO> bestSellerDTOS,
        List<WeekBestSellerDTO> weekBestSellerDTOS,
        DayBestSellerDTO dayBestSellerDTO,
        List<BookHistoryDTO> bookHistoryDTOS
) {

    @Builder
    public record BestSellerDTO(
            Integer id,
            String bookImagePath,
            String bookTitle,
            String author
    ) {
    }

    @Builder
    public record WeekBestSellerDTO(
            Integer id,
            String bookImagePath,
            String bookTitle,
            String author
    ) {

    }

    @Builder
    public record DayBestSellerDTO(
            Integer id,
            String bookTitle,
            String author,
            String bookIntro
    ) {

    }

    @Builder
    public record BookHistoryDTO(
            Integer userId,
            Integer bookId,
            String bookTitle,
            String pageCount,
            String lastReadPage
    ) {

    }
}
