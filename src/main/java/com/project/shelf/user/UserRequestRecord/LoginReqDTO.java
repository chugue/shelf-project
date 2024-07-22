package com.project.shelf.user.UserRequestRecord;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginReqDTO(

        @Email(message = "올바른 이메일 형식이 아닙니다")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,
        @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하여야 합니다")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
