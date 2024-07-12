package com.project.shelf.user.UserResponseRecord;

import com.project.shelf.user.UserResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MainDTO(
        List<BestSellerDTO> bestSellerDTOS,
        List<WeekBestSellerDTO> weekBestSellerDTOS,
        List<DayBestSellerDTO> dayBestSellerDTOS,
        List<BookHistoryDTO> bookHistoryDTOS
        ) {

    @Builder
    public record BestSellerDTO(
            Integer id,
            String bookFilePath,
            String bookTitle,
            String author,
            LocalDateTime firstRead,
            LocalDateTime lastRead
    ){
    }

    @Builder
    public record WeekBestSellerDTO(
            Integer id,
            String bookFilePath,
            String bookTitle,
            String author,
            LocalDateTime firstRead,
            LocalDateTime lastRead
    ){

    }

    @Builder
    public record DayBestSellerDTO(
        Integer id,
        String bookTitle,
        String author,
        String bookIntro,
        LocalDateTime firstRead,
        LocalDateTime lastRead
    ){

    }

    @Builder
    public record BookHistoryDTO(
        Integer id,
        String bookTitle,
        String pageCount,
        String lastReadPage,
        LocalDateTime firstRead, //처음으로 읽은 날짜
        LocalDateTime lastRead
    ){

    }
}
