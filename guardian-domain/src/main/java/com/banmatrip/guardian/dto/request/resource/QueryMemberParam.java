package com.banmatrip.guardian.dto.request.resource;

import lombok.Data;

/**
 * @author jepson
 * @Description: 查询成员参数
 * @create 2017-10-25 12:13
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class QueryMemberParam {

    /**账号**/
    private String account;
    /**用户ID**/
    private String userId;
}