package com.project.shelf.user.UserRequestRecord;

import lombok.Builder;

@Builder
public record LoginReqDTO(
        String email,
        String password
) {
}
