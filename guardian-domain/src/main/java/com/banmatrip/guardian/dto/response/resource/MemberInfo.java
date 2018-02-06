package com.banmatrip.guardian.dto.response.resource;

import lombok.Data;

/**
 * @author jepson
 * @Description: 成员信息
 * @create 2017-10-25 11:58
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class MemberInfo {

    /**成员ID**/
    private String id;
    /**老系统成员ID**/
    private String contrastId;
    /**账户**/
    private String account;
    /**姓名**/
    private String name;
    /**邮箱**/
    private String email;
    /**部门组ID**/
    private String departmentGroupId;
    /**员工编号**/
    private String employeId;
    /**手机号**/
    private String cellphone;
    /**族群**/
    private String ethnicName;
    /**岗位**/
    private String positionName;
    /**序列**/
    private String serialName;
}