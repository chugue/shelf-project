package com.project.shelf._core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.shelf.user.SessionUser;
import com.project.shelf.user.User;

import java.util.Date;

public class AppJwtUtil {
    public static String create(User user) {
        // JWT 토큰 생성
        String jwt = JWT.create()
                // 토큰의 주제(subject) 설정
                .withSubject("blog")
                // 토큰 만료 시간 설정 (현재 시간 + 24시간)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                // 토큰에 사용자의 정보(Claim) 설정
                .withClaim("id", user.getId()) // 사용자 ID
                .withClaim("name", user.getNickName())
                .withClaim("email", user.getEmail()) // 사용자 이메일
                // 토큰 서명(Sign) 설정 (암호화 알고리즘은 HMAC512 사용)
                .sign(Algorithm.HMAC512("finalproject"));

        return jwt; // 생성된 JWT 토큰 반환
    }

    // JWT 토큰 검증 및 복호화 메서드
    public static SessionUser verify(String jwt) {

        // JWT 토큰 복호화 및 검증
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("finalproject")).build().verify(jwt);
        // 토큰에서 사용자 정보(Claim) 추출
        int id = decodedJWT.getClaim("id").asInt(); // 사용자 ID
        String nickName = decodedJWT.getClaim("name").asString(); // 사용자 이름
        String email = decodedJWT.getClaim("email").asString(); // 사용자 이메일

        // 복호화된 사용자 정보를 SessionAdmin 객체로 변환하여 반환
        return SessionUser.builder()
                .id(id)
                .nickName(nickName)
                .email(email)
                .build();
    }
}
