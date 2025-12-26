package org.example.bigzuoye.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数错误
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleIllegalArgument(IllegalArgumentException e) {
        return Result.error(ResultCode.BAD_REQUEST, e.getMessage());
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<?> handleNullPointer(NullPointerException e) {
        return Result.error(ResultCode.ERROR, "空指针异常");
    }

    /**
     * 兜底异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        e.printStackTrace();
        return Result.error(ResultCode.ERROR);
    }
}
