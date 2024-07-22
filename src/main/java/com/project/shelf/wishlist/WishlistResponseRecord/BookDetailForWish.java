package com.project.shelf.wishlist.WishlistResponseRecord;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BookDetailForWish(
        Integer id,
        Integer bookId,
        String bookImagePath,
        String bookTitle,
        String author,
        LocalDateTime createdAt
        ){

}
