package com.project.shelf.user.UserResponseRecord;

import lombok.Builder;

@Builder
public record MonthlyUserDTO(
        Integer year,
        Integer month,
        Long userCount
){}
