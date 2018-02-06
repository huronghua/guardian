package com.banmatrip.guardian.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jepson
 * @Description:  TOKEN注销/失效
 * @create 2017-11-02 21:39
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Controller
public class InvalidateTokenController {

    private static final Logger log = LoggerFactory.getLogger(InvalidateTokenController.class);

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 注销
     * @param accessToken
     * @return
     */
    @RequestMapping(value="/invalidate/token", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, String> logout(@RequestParam(name = "access_token") String accessToken, HttpServletRequest request) {
        log.debug("Invalidating token {}", accessToken);
        consumerTokenServices.revokeToken(accessToken);
        /**清除服务端session**/
        request.getSession().invalidate();
        Map<String, String> ret = new HashMap<>();
        ret.put("access_token", accessToken);
        return ret;
    }
}