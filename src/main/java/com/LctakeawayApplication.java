package com;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("com.mapper")
public class LctakeawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LctakeawayApplication.class, args);
        log.info("wossb");
    }

}
