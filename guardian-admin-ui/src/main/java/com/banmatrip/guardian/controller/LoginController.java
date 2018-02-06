package com.banmatrip.guardian.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.awt.SunHints;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author jepson
 * @Description: 登录等出控制器
 * @create 2017-09-20 11:27
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Controller
public class LoginController {

    @Value("${sso.tosignout.url}")
    String ssoTosignoutUrl;

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping("/tosignout")
    public String tosso(ModelMap modelMap, HttpServletRequest request,HttpServletResponse response) throws IOException {
        modelMap.put("ssoTosignoutUrl",ssoTosignoutUrl);
       /* request.getSession().invalidate();
        Cookie[] cookies =  request.getCookies();
        for(Cookie cookie : cookies){
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }*/
        return "tosignout";
    }


    /**
     * 登录
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "redirect:/#/";
    }


    /**
     * 主页
     * @param model
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request,ModelMap model, Principal user) throws Exception{
        model.addAttribute("user", user);
        request.getSession().setAttribute("user",user);
        return "index";
    }
}