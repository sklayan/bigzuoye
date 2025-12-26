package org.example.bigzuoye.security;

import io.jsonwebtoken.JwtException;
import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.common.ResultCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class JwtFilter implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String token = request.getHeader("Authorization");

        // 1. 没有 token
        if (token == null || token.isEmpty()) {
            writeError(response, "未登录，请先登录");
            return false;
        }

        try {
            // 2. 解析 token
            Long userId = jwtUtil.getUserIdFromToken(token);

            // 3. 保存当前用户
            UserContext.setUserId(userId);

            return true;
        } catch (JwtException e) {
            writeError(response, "登录已过期或无效");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        // 防止内存泄漏
        UserContext.clear();
    }

    private void writeError(HttpServletResponse response, String msg) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(Result.error(ResultCode.UNAUTHORIZED, msg).toJson());
        writer.flush();
        writer.close();
    }
}
