package com.project.shelf.wishlist.WishlistRequestRecord;

import lombok.Builder;

@Builder
public record LoginReqDTO(
        String email,
        String password
) {
}
