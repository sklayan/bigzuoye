package org.example.bigzuoye.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 明文密码加密
     */
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * 校验密码
     */
    public static boolean match(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
