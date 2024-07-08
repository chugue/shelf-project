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

        if(sessionAdmin != null){
            return true;
        }
        throw new Exception401("로그인 하셔야 해요");
    }
}