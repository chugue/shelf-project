package com.project.shelf.user.UserResponseRecord;

import lombok.Builder;

import java.util.List;

@Builder
public record MyLibraryResponseDTO(
        List<BookListDTO> bookList,
        List<WishListDTO> wishList
) {
    @Builder
    public record BookListDTO(
            List<HistoryDTO> historyList,
            List<AllBookDTO> allBook
    ){
        @Builder
        public record HistoryDTO(
                Integer id,
                String imagePath,
                String bookTitle,
                Integer pageCount,
                Integer lastReadPage
        ){
        }

        @Builder
        public record AllBookDTO(
                Integer id,
                String bookImagePath,
                String bookTitle,
                String author
        ){
        }
    }
    @Builder
    public record WishListDTO(
            Integer id,
            String bookImagePath,
            Integer bookId,
            String bookTitle,
            String author,
            String createdAt
    ){
    }
}
