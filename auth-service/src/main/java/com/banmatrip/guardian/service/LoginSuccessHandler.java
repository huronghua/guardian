package com.banmatrip.guardian.service;

import com.banmatrip.guardian.domain.LoginLog;
import com.banmatrip.guardian.dto.response.user.UserSecurityDetail;
import com.banmatrip.guardian.interfaces.login.LoginService;
import com.banmatrip.guardian.vo.login.UserVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author jepson
 * @Description: 登录成功处理
 * @create 2017-09-18 14:48
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    protected Log log = LogFactory.getLog(LoginSuccessHandler.class);

    @Autowired
    LoginService loginService;

    /**
     * 认证成功
     *
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserSecurityDetail userDetails = (UserSecurityDetail)authentication.getPrincipal();
        log.info("登录用户user:" + userDetails.getName() + "login"+request.getContextPath());
        log.info("IP:" + getIpAddress(request));
        super.onAuthenticationSuccess(request, response, authentication);
        LoginLog loginLog = new LoginLog();
        loginLog.setLoginAccount(userDetails.getAccount());
        loginLog.setLoginName(userDetails.getName());
        loginLog.setLoginTime(new Date());
        loginLog.setLoginIp(getIpAddress(request));
        loginService.insertLoginLog(loginLog);
    }


    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}