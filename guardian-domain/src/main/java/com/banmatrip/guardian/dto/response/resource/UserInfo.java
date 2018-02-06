package com.banmatrip.guardian.dto.response.resource;

import lombok.Data;

/**
 * Created by zhangwei on 2017/11/16.
 */
@Data
public class UserInfo {
    /*人员id*/
    private Integer id;
    /*人员姓名*/
    private String name;
    /*主管标识*/
    private Integer managerFlag;
}
