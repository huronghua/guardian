package com.banmatrip.guardian.dto.request.resource;

import lombok.Data;

/**
 * @author jepson
 * @Description: 查询部门参数
 * @create 2017-10-25 12:50
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class QueryDepartmentParam {

    /**部门组ID**/
    private String departmentGroupId;
    /**部门ID**/
    private String departmentId;
}