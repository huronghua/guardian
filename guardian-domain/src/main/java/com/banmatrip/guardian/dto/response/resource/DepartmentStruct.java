package com.banmatrip.guardian.dto.response.resource;

import lombok.Data;

/**
 * @author jepson
 * @Description: 部门结构
 * @create 2017-10-25 12:58
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class DepartmentStruct {

    /**部门名称**/
    private String departmentName;
    /**部门分级**/
    private String departmentLevel;
    /***部门负责人*/
    private String chargName;
}