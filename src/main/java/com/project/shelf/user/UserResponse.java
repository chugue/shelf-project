package com.project.shelf.user;

import lombok.Data;

public class UserResponse {

    @Data
    public static class Join {
        private Integer id;
        private String email;
        private String password;
        private String nickName;

        public Join(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.nickName = user.getNickName();
        }
    }

    // 마이페이지
    @Data
    public static class MyPageDTO {
        private Integer id;
        private String nickNam;
        //        private String profileIconPath; // 필드가 없음.
        private Boolean isSubscribe;

        public MyPageDTO(User user) {
            this.id = user.getId();
            this.nickNam = user.getNickName();
            this.isSubscribe = user.getIsSubscribed();
        }
    }

    // 개인정보 조회
    @Data
    public static class MyInfoDTO {
        private Integer id;
        private String nickName;
        private String email;
        private String password;
        private String phone;
        // 추가로직
//        private String cardLastNum;   // 카드 번호 마지막 4
//        private String ProfileIconPath;   // 프로필 아이콘 경로

        private String address;

        public MyInfoDTO(User user) {
            this.id = user.getId();
            this.nickName = user.getNickName();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.phone = user.getPhone();
            this.address = user.getAddress();
//            this.cardLastNum = user.getIsSubscribed().get__;
//            this.profileIconPath = user.getProfileIcon.getPath();
        }
    }

    // 개인정보 수정
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
            this.nickName = user.getNickName();
            this.password = user.getPassword();
            this.phone = user.getPhone();
            this.address = user.getAddress();
//            this.cardLastNum = user.getIsSubscribed().get__;
//            this.profileIconPath = user.ProfileIconId.getId();
        }
    }
}
