package com.banmatrip.guardian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 *  create by jepson on 2017/09/13
 */
@SpringBootApplication
@EnableResourceServer
@ComponentScan(basePackages = "com.banmatrip.guardian")
@MapperScan("com.banmatrip.guardian.repository.mapper")
public class ResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceApplication.class,args);
    }
}
