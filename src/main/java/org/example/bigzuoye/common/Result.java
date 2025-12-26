package org.example.bigzuoye.common;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Result<T> {

    private int code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /* ---------- 成功 ---------- */

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(), data);
    }

    /* ---------- 失败 ---------- */

    public static <T> Result<T> error(ResultCode code) {
        return new Result<>(code.getCode(), code.getMessage(), null);
    }

    public static <T> Result<T> error(ResultCode code, String message) {
        return new Result<>(code.getCode(), message, null);
    }

    /* ---------- getter / setter ---------- */

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /* ---------- JSON 输出（给 JwtFilter 用） ---------- */

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "{\"code\":500,\"message\":\"json error\"}";
        }
    }
}
