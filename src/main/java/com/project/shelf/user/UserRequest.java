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
}
