package com.project.shelf.user.UserResponseRecord;

import com.project.shelf.user.User;
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
    public User toUser() {
        return User.builder()
                .email(email)
                .nickName(nickName)
                .phone(phone)
                .address(address)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
