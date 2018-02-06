package com.banmatrip.guardian.dto.response.resource;

import lombok.Data;

/**
 * @author Miracle Xu
 * @Description:
 * @create 2018-01-16 10:57
 * @Copyright: 2018 www.banmatrip.com All rights reserved.
 **/
@Data
public class UserDetailInfo {
    /*人员id*/
    private Integer id;
    /*人员姓名*/
    private String name;
    /*主管标识*/
    private String employeeId;
}
