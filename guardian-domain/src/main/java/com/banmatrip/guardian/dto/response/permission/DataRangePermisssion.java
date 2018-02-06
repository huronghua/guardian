package com.banmatrip.guardian.dto.response.permission;

import lombok.Data;

/**
 * @author jepson
 * @Description: 数据范围权限实体
 * @create 2017-09-20 19:57
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class DataRangePermisssion {

    /**资源类型**/
    String destinationType;
    /**产品类型**/
    String productType;
    /**平台类型**/
    String platformType;
    /**资源类型**/
    String resourceType;

}