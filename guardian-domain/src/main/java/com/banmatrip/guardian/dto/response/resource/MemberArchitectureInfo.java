package com.banmatrip.guardian.dto.response.resource;

import lombok.Data;

import java.util.List;

/**
 * @author jepson
 * @Description: 成员架构信息
 * @create 2017-10-25 11:58
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class MemberArchitectureInfo {

    /**成员信息**/
    private MemberInfo memberInfo;
    /**部门结构**/
    private List<DepartmentStruct> departmentStructs;
}