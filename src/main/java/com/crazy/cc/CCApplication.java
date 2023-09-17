package com.crazy.cc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CCApplication {
    public static void main(String[] args) {
        SpringApplication.run(CCApplication.class,args);
        log.info("项目启动成功...");
    }
}