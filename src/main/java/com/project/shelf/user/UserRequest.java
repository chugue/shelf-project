package com.project.shelf.user;

import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    @NoArgsConstructor
    @Data
    public static class JoinDTO{
        private String email;
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
