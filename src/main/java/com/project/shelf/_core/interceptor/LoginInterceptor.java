package com.project.shelf._core.interceptor;

import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf.admin.Admin;
import com.project.shelf.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle............");
        HttpSession session = request.getSession();

        Admin sessionAdmin = (Admin) session.getAttribute("sessionAdmin");
        User sessionUser = (User) session.getAttribute("sessionUser");

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api-admin")) {
            if (sessionAdmin == null) {
                throw new Exception401("잘못된 접근입니다.");
            }
        } else if (requestURI.startsWith("/api-user")) {
            if (sessionUser == null) {
                throw new Exception401("고객 로그인이 필요합니다");
            }
        }
        return true;
    }
}