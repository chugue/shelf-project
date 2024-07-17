package com.project.shelf.admin;


import lombok.Data;

public class AdminRequest {

    // 로그인 요청
    @Data
    public static class LoginDTO{
        private String email;
        private String password;
    }
}
