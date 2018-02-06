package com.banmatrip.guardian.vo.role;

import lombok.Data;

import java.util.Date;

/**
 * Created by banma on 2017/9/19.
 */
@Data
public class RoleGroupVo {
    private Integer id;

    private String roleGroupName;

    private Date createTime;

    private Date updateTime;

    private Integer createId;

    private Integer updateId;

}
