package com.hyx.hyxmovieweb.entity;

public class Result {
    public int code;
    public String message;
    public Object data;

    public static Result ok(Object data) {
        Result res = new Result();
        res.code = 200;
        res.data = data;

        return res;
    }

    public static Result error(String msg, Object errors) {
        Result res = new Result();
        res.code = 400;
        res.message = msg;
        res.data = errors;

        return res;
    }

    public static Result error(String msg) {
        return error(msg, null);
    }
}