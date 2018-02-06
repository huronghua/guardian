package com.banmatrip.guardian.vo.login;

import lombok.Data;

/**
 * @author jepson
 * @Description:
 * @create 2017-09-22 23:53
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class UserVo {

    private Integer id;
    private String name;
    private String account;
    private String orangeAccount;
    private String cellphone;
    private String roleType;
}