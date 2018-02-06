package com.banmatrip.guardian.controller;

import com.banmatrip.guardian.dto.response.base.RestResponse;
import com.banmatrip.guardian.dto.response.resource.AllUserDetailInfo;
import com.banmatrip.guardian.dto.response.resource.MemberArchitectureInfo;
import com.banmatrip.guardian.dto.response.resource.UserInfo;
import com.banmatrip.guardian.interfaces.resource.MemberResourceService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jepson
 * @Description: 用户资源接口
 * @create 2017-09-19 9:51
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@RestController
public class UserController {

    /**日志句柄**/
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    MemberResourceService memberResourceService;

    @GetMapping(value = "/user")
    public RestResponse getUser(Principal principal) {
        Map<String, Object> userMap = new HashMap<String,Object>();
        try {
            MemberArchitectureInfo memberArchitectureInfo = memberResourceService.getMemberArchitectureInfoByAccount(principal.getName());
            userMap.put("memberArchitectureInfo",memberArchitectureInfo);
        } catch(Exception e) {
            log.error("查询成员架构信息失败" + e.getCause());
            return new RestResponse(false,"查询成员架构信息失败" + e.getMessage());
        }
        log.info(principal.getName() + "查询成员架构信息成功!" + new Gson().toJson(userMap).toString());
        return new RestResponse(true, "查询成员架构信息成功", "S00001", userMap);
    }

    @GetMapping(value = "/department_member")
    public RestResponse getDepartmentMember(Principal principal) {
        List<Map<Integer,List<UserInfo>>> memberList=new ArrayList<Map<Integer,List<UserInfo>>>();
        try {
            memberList=memberResourceService.getDepartmentMemberInfoByAccount(principal.getName());
        } catch(Exception e) {
            log.error("查询部门成员信息失败" + e.getCause());
            return new RestResponse(false,"查询部门成员信息失败" + e.getMessage());
        }
        return new RestResponse(true, "查询部门成员信息成功", "S00001", memberList);
    }

    @GetMapping(value = "/accounts")
    public RestResponse getAccounts(Principal principal,@RequestParam String ids) {
        AllUserDetailInfo userDetailList=new AllUserDetailInfo();
        try {
            userDetailList=memberResourceService.getUserInfo(ids);
        } catch(Exception e) {
            log.error("查询成员架构信息失败" + e.getCause());
            return new RestResponse(false,"查询斑马账户信息失败" + e.getMessage());
        }
        log.info(principal.getName() + "查询斑马账户信息成功!");
        return new RestResponse( true,"查询斑马账户信息成功", "200", userDetailList);
    }
}