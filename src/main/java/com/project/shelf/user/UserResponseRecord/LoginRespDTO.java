package com.project.shelf.user.UserResponseRecord;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record LoginRespDTO(
         String email,
         String nickName,
         String phone,
         String address,
         LocalDate createdAt,
         LocalDate updatedAt
) {
}
