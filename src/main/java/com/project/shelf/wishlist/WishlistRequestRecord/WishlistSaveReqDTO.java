package com.project.shelf.wishlist.WishlistRequestRecord;

import lombok.Builder;

@Builder
public record WishlistSaveReqDTO(
        Integer userId,
        Integer bookId
) {
}
