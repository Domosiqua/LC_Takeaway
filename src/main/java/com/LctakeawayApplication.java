package com;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@MapperScan("com.mapper")
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching
@EnableKnife4j
public class LctakeawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LctakeawayApplication.class, args);
    }

}
