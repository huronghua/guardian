package com.banmatrip.guardian.controller;

import com.banmatrip.guardian.dto.response.base.RestResponse;
import com.banmatrip.guardian.interfaces.permission.RolePermissionService;
import com.banmatrip.guardian.vo.permission.QueryFunctionPermissionVo;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * @author jepson
 * @Description: 权限控制器
 * @create 2017-09-20 18:10
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

    /**日志句柄**/
    private static final Logger log = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    RolePermissionService rolePermissionService;

    /**
     * 获取功能权限
     *
     * @return
     */
    @GetMapping(value = "/function")
    public RestResponse getFunctionPermission(Principal user) {
        Map<String, Object> resultMap;
        try {
            QueryFunctionPermissionVo queryParam = new QueryFunctionPermissionVo();
            queryParam.setLoginAccount(user.getName());
            resultMap = rolePermissionService.getFunctionPermission(queryParam);
        } catch(Exception e) {
            log.error("查询功能权限失败" + e.getCause());
            return new RestResponse(false,"查询功能权限失败" + e.getMessage());
        }
        log.info(user.getName() + "查询功能权限成功" + new Gson().toJson(resultMap).toString());
        return new RestResponse(true, "查询功能权限成功", "S0001", resultMap);
    }


    /**
     * 获取数据权限
     *
     * @return
     */
    @GetMapping(value = "/data")
    public RestResponse getDataPermission(Principal user) {
        Map<String, Object> resultMap;
        try {
             resultMap = rolePermissionService.getDataPermission(user.getName());
        } catch(Exception e) {
            log.error("查询数据权限失败" + e.getCause());
            return new RestResponse(false,"查询数据权限失败" + e.getMessage());
        }
        log.info(user.getName() + "查询数据权限成功!" + new Gson().toJson(resultMap).toString());
        return new RestResponse(true, "查询数据权限成功", "S0002", resultMap);
    }

    /**
     * 获取orange功能权限
     *
     * @return
     */
    @GetMapping(value = "/orangeFunction")
    public RestResponse getOrangeFunctionPermission(HttpServletRequest request, Principal user) {

        Map<String, Object> resultMap;
        try {
            QueryFunctionPermissionVo queryParam = new QueryFunctionPermissionVo();
            queryParam.setLoginAccount(user.getName());
            queryParam.setFunctionType(request.getParameter("type"));
            resultMap = rolePermissionService.getOrangeFunctionPermission(queryParam);
        } catch(Exception e) {
            log.error("查询功能权限失败" + e.getCause());
            return new RestResponse(false,"查询功能权限失败" + e.getMessage());
        }
        log.info(user.getName() + "查询功能权限成功" + new Gson().toJson(resultMap).toString());
        return new RestResponse(true, "查询功能权限成功", "S0001", resultMap);
    }
}