package com.project.shelf._core.erros.exception;

public class SSRException401 extends RuntimeException{
    private final Integer errorCode = 401;
    public SSRException401(String msg) {
        super(msg);
    }
}
