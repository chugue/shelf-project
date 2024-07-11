package com.project.shelf._core.util;

import lombok.Data;

@Data
public class ApiUtil<T> {

    private Integer status;
    private String msg;
    private T data;

    public ApiUtil(T body) {
        this.status = 200;
        this.msg = "성공";
        this.data = body;
    }

    public ApiUtil(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = null;
    }
}
