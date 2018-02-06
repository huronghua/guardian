package com.banmatrip.guardian.interceptor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jepson
 * @Description:  获取session请求拦截器
 * @create 2017-10-19 18:29
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class SessionInterceptor implements HandlerInterceptor {

    private static final Logger logger = LogManager.getLogger(SessionInterceptor.class);
    /**
     * 前置处理
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(httpServletRequest.getRequestURI().equals("/tosignout")||httpServletRequest.getRequestURI().equals("/")){
            return true;
        }
        if(httpServletRequest.getSession().getAttribute("user")==null){
            httpServletResponse.sendRedirect("/tosignout");
            return false;
        }
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        /*if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            if(SecurityContextHolder.getContext().getAuthentication()!=null) {
                 OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
                String userName = (String) oAuth2Authentication.getUserAuthentication().getPrincipal();
                String requestToken = oAuth2AuthenticationDetails.getTokenValue();
                *//**没有登录重新登录**//*
                if (null == userName || null == requestToken) {
                    logger.info("登录用户名：" + userName);
                    httpServletResponse.sendRedirect("/tosignout");
                    return false;
                }
            }
        }*/
        return true;
    }

    /**
     * 后置处理
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     *  完成后处理
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
