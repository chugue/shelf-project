package com.project.shelf._core.erros;

import com.project.shelf._core.erros.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MySSRExceptionHandler {

    @ExceptionHandler(SSRException400.class)
    public String ex400(SSRException400 e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        log.warn("400 : " + e.getMessage());
        return "err/err-page";
    }

    @ExceptionHandler(SSRException401.class)
    public String ex401(SSRException401 e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        request.setAttribute("errorCode", 401);
        log.warn("401   : " + e.getMessage());
        log.warn("IP    : " + request.getRemoteAddr());
        log.warn("WAY   : " + request.getHeader("User-Agent"));
        return "err/err-page";
    }

    @ExceptionHandler(SSRException403.class)
    public String ex403(SSRException403 e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        log.warn("403 : " + e.getMessage());
        return "err/err-page";
    }

    @ExceptionHandler(SSRException404.class)
    public String ex404(SSRException404 e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        log.info("404 : " + e.getMessage());
        return "err/err-page";
    }

    @ExceptionHandler(SSRException500.class)
    public String ex500(RuntimeException e, HttpServletRequest request) {
        request.setAttribute("msg", e.getMessage());
        log.error("500 : " + e.getMessage());
        return "err/err-page";
    }
}
