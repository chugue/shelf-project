package com.project.shelf._core.handler;

import com.project.shelf._core.erros.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice // 비정상적일때 처리하는 응답 에러 컨트롤러 -> view(파일)를 리턴 -> @ResponseBody붙이면 됨
// @RestControllerAdvice : 응답을 직접 반환
// @Controller -> @ResponseBody붙이면 됨
public class MyExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e, HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        log.warn("400 : " + e.getMessage());
        return "err/400";
    }
    @ExceptionHandler(Exception401.class)
    public String ex401(Exception401 e, HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        log.warn("401 : " + e.getMessage());
        log.warn("IP : " + request.getRemoteAddr());
        log.warn("WAY : " + request.getHeader("User-Agent"));
        return "err/401";
    }
    @ExceptionHandler(Exception403.class)
    public String ex403(RuntimeException e, HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        log.warn("403 : " + e.getMessage());
        return "err/403";
    }
    @ExceptionHandler(Exception404.class)
    public String ex404(RuntimeException e, HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        log.info("404 : " + e.getMessage());
        return "err/404";
    }

    @ExceptionHandler(Exception500.class)
    public String ex500(RuntimeException e, HttpServletRequest request){
        request.setAttribute("msg", e.getMessage());
        log.error("500 : " + e.getMessage());
        return "err/500";
    }

    // 어떤 에러가 나타날때 처리할지 -> 분기해야함
//    @ExceptionHandler(Exception.class)
//    public @ResponseBody String error1(Exception e) {
//        return Script.back(e.getMessage());
//    }

}