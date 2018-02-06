package com.banmatrip.guardian.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author jepson
 * @Description:
 * @create 2017-09-22 23:06
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class LoginLog {

    /**ID**/
    private Long id;
    /**登录账号**/
    private String loginAccount;
    /**登录名称**/
    private String loginName;
    /**登录时间**/
    private Date loginTime;
    /**登出时间**/
    private Date signoutTime;
    /**登录IP**/
    private String loginIp;
    /**登录MAC地址**/
    private String loginMac;
}