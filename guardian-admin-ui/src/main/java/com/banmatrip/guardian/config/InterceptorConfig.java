package com.banmatrip.guardian.config;

import com.banmatrip.guardian.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author jepson
 * @Description: 拦截器配置
 * @create 2017-10-19 19:42
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
    }
}
