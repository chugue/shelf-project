package com.project.shelf._core.interceptor;

import com.project.shelf._core.erros.exception.Exception401;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.project.shelf._core.erros.exception.Exception500;
import com.project.shelf._core.util.AppJwtUtil;
import com.project.shelf.user.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AppInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle............");

        String jwt = request.getHeader("Authorization");
        jwt = jwt.replace("Bearer ", "");
        System.out.println("jwt = .▶️▶️▶️▶️▶️" + jwt);
        HttpSession session = request.getSession();


        try {
            SessionUser sessionUser = AppJwtUtil.verify(jwt);
            session.setAttribute("sessionUser", sessionUser);
        } catch (TokenExpiredException e) {
            throw new Exception401("토큰 만료 시간이 지났습니다. 다시 로그인 하세요");
        } catch (JWTDecodeException e) {
            throw new Exception401("토큰이 유효하지 않습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 개발 진행 시 TEST 보기
            throw new Exception500(e.getMessage()); //알수 없는 오류는 다 500으로 던진다.
        }
        return true;
    }

}
