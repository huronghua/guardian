package com.banmatrip.guardian.controller;

import com.banmatrip.guardian.domain.LoginLog;
import com.banmatrip.guardian.dto.response.base.RestResponse;
import com.banmatrip.guardian.dto.response.user.UserSecurityDetail;
import com.banmatrip.guardian.interfaces.login.LoginService;
import com.banmatrip.guardian.vo.login.SystemConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * @author jepson
 * @Description: 登录控制器
 * @create 2017-09-18 22:55
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Value("${sso.index.url}")
    String ssoIndexUrl;

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 登出
     * @param authentication
     * @return
     */
    @RequestMapping("/signout")
    @ResponseBody
    public RestResponse signout(Authentication authentication) throws Exception{
        try {
            UserSecurityDetail userDetails = (UserSecurityDetail)authentication.getPrincipal();
            /**查询最近登录记录*/
            LoginLog loginLog = loginService.getLatestLoginLogByAccount(userDetails.getAccount());
            loginLog.setSignoutTime(new Date());
            loginLog.setId(loginLog.getId());
            loginService.updateSignoutLog(loginLog);
        } catch(Exception e) {
            return new RestResponse(false,"登出失败!" + e.getMessage());
        }
        return new RestResponse(true,"登出成功!");
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        modelMap.put("ssoIndexUrl",ssoIndexUrl);
        return "index";
    }


    /**
     * home页
     *
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap modelMap) {
        List<SystemConfigVo> configList = loginService.getSystemConfigList();
        modelMap.put("configList", configList);
        return "home";
    }
}