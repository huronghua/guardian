package com.banmatrip.guardian.dto.response.resource;

import lombok.Data;

/**
 * @author jepson
 * @Description: 部门信息
 * @create 2017-10-25 11:59
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class DepartmentInfo {

    /**部门组ID**/
    private String departmentGroupId;
    /**部门ID**/
    private String departmentId;
    /**上级部门ID**/
    private String parentDepartmentId;
    /**部门级别**/
    private String departmentLevel;
    /**部门级别名称**/
    private String departmentLevelName;
    /**部门名称**/
    private String departmentName;
    /**主管名称**/
    private String chargeName;
}