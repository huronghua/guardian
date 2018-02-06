package com.banmatrip.guardian.vo.role;

import lombok.Data;

import java.util.Date;

/**
 * Created by banma on 2017/9/19.
 */
@Data
public class RoleVo {
    private Integer roleId;

    private String roleName;

    private Integer groupId;

    private Date createTime;

    private Date updateTime;

    private Integer createId;

    private Integer updateId;

    private Integer destinationType;

    private Integer productType;

    private Integer platformType;

    private Integer resourceType;

}
