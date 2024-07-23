package com.project.shelf.user;

import com.project.shelf._core.enums.Avatar;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    // 회원가입 DTO
    @NoArgsConstructor
    @Data
    public static class JoinDTO{
        @Email(message = "올바른 이메일 형식이 아닙니다!")
        @NotBlank(message = "이메일은 필수 항목입니다!")
        private String email;

        @Size(min = 2, max = 12, message = "닉네임은 2자 이상, 12자 이하여야 합니다!")
        @NotBlank(message = "닉네임은 필수 항목입니다!")
        private String nickName;

        @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하여야 합니다!")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;

        public JoinDTO(String email, String nickName, String password) {
            this.email = email;
            this.nickName = nickName;
            this.password = password;
        }
    }

    // 개인정보 수정 DTO
    @NoArgsConstructor
    @Data
    public static class UpdateInfoDTO {

        private Avatar avatar;
        @Size(min = 2, max = 12, message = "닉네임은 2자 이상, 12자 이하여야 합니다")
        @NotBlank(message = "닉네임은 필수 항목입니다!")
        private String nickName;

        @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하여야 합니다")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;

        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호는 '000-0000-0000' 형식이어야 합니다.")
        private String phone;
        @NotBlank(message = "주소를 입력해주세요.")
        private String address;

        public UpdateInfoDTO(User user) {
            this.avatar     = user.getAvatar();
            this.nickName   = user.getNickName();
            this.password   = user.getPassword();
            this.phone      = user.getPhone();
            this.address    = user.getAddress();
        }
    }
}
