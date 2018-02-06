package com.banmatrip.guardian.dto.response.user;

import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author jepson
 * @Description: 用户安全详情实体
 * @create 2017-09-18 15:54
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class UserSecurityDetail {

    private String account;

    private String name;

    private String password;

    private String email;

    private String cellphone;

    private String employeeId;

    private String orangeAccount;

    private String positionId;

    private Date createTime;

    private Date updateTime;

    private Integer createId;

    private Integer updateId;

    private List<Role> roles;

    private List<Department> departments;
}