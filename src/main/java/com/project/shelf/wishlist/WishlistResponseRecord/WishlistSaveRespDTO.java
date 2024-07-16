package com.project.shelf.wishlist.WishlistResponseRecord;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WishlistSaveRespDTO(
        Integer id,
        Integer userId,
        Integer bookId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
