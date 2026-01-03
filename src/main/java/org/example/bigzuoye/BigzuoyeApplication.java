package org.example.bigzuoye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.bigzuoye.mapper")

public class BigzuoyeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigzuoyeApplication.class, args);
    }

}
