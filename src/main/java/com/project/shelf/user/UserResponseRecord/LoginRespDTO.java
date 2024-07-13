package com.project.shelf.user.UserResponseRecord;

import com.project.shelf.user.User;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

/*
  int id;
  String email;
  String nickName;
  bool status;
  DateTime? createdAt;
 */
@Builder
public record LoginRespDTO(
        Integer userId,
        String email,
        String nickName,
        Boolean status,
        LocalDateTime createdAt
) {
    public User toUser() {
        return User.builder()
                .id(userId)
                .email(email)
                .nickName(nickName)
                .status(status)
                .createdAt(createdAt)
                .build();
    }
}
