package com.project.shelf.book.BookResponseRecord;

import lombok.Builder;

import java.util.List;

@Builder
public record RankResponseDTO(
        List<TotalBestSellerDTO> totalBestSellers,
        List<CategoryByBestSellerDTO> categoryByBestSellers
) {
    @Builder
    public record TotalBestSellerDTO(
            Integer id,
            String bookImagePath,
            String bookTitle,
            String author,
            Integer rankNum
    ){
    }

    @Builder
    public record CategoryByBestSellerDTO(
            Integer id,
            String path,
            String title,
            String authorName,
            Integer rankNum,
            String category
    ){

    }
}