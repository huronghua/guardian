package com.banmatrip.guardian.vo.permission;

import lombok.Data;

/**
 * @author jepson
 * @Description: 查询功能权限 VO
 * @create 2017-09-23 14:05
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class QueryFunctionPermissionVo {

    /**登录账号**/
    private String loginAccount;
    /**功能类型**/
    private String functionType;
}