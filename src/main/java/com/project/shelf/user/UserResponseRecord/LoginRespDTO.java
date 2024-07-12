package com.project.shelf.user.UserResponseRecord;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record LoginRespDTO(
         String email,
         String nickName,
         String phone,
         String address,
         LocalDateTime createdAt,
         LocalDateTime updatedAt
) {
}
