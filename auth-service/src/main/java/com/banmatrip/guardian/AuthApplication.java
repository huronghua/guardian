package com.banmatrip.guardian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * create by jepson on 2017/09/12
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.banmatrip.guardian")
@MapperScan("com.banmatrip.guardian.repository.mapper")
public class AuthApplication implements ServletContextInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        servletContext.getSessionCookieConfig().setName("SESSIONID");
    }
}
