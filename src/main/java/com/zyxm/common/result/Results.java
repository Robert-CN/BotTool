package com.zyxm.common.result;

import com.zyxm.common.enums.ResultEnum;

/**
 * @Author Robert
 * @Create 2020/9/21
 * @Desc TODO
 **/
public class Results<T> {
    private static <T> Result<T> build(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    private static <T> Result<T> build(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> success() {
        return build(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.msg);
    }

    public static <T> Result<T> success(String msg, T data) {
        return build(ResultEnum.SUCCESS.code, msg, data);
    }

    public static <T> Result<T> success(T data) {
        return build(ResultEnum.SUCCESS.code, ResultEnum.SUCCESS.msg, data);
    }

    public static <T> Result<T> failure() {
        return build(ResultEnum.FAILURE.code, ResultEnum.FAILURE.msg);
    }

    public static <T> Result<T> failure(Integer code, String msg) {
        return build(code, msg);
    }

    public static <T> Result<T> failure(String msg) {
        return build(ResultEnum.FAILURE.code, msg);
    }
}
