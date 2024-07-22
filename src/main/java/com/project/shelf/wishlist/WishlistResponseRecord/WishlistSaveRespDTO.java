package com.project.shelf.wishlist.WishlistResponseRecord;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WishlistSaveRespDTO(
        Integer userId,
        Integer bookId,
        Boolean isWish,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
