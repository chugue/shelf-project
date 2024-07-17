package com.project.shelf.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    @NoArgsConstructor
    @Data
    public static class JoinDTO{
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        private String email;
        @Size(min = 2, max = 12, message = "닉네임은 4자 이상 12자 이하여야 합니다.")
        @NotBlank(message = "닉네임은 필수 항목입니다.")
        private String nickName;
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
        private String nickName;
        private String password;
        private String phone;
        private String address;

        // 추가로직
//        private String cardLastNum;       // 카드 번호 마지막 4
//        private Integer ProfileIconId;    // 프로필 아이콘


        public UpdateInfoDTO(User user) {
            this.nickName   = user.getNickName();
            this.password   = user.getPassword();
            this.phone      = user.getPhone();
            this.address    = user.getAddress();
//            this.cardLastNum = user.getIsSubscribed().get__;
//            this.profileIconPath = user.ProfileIconId.getId();
        }
    }
}
