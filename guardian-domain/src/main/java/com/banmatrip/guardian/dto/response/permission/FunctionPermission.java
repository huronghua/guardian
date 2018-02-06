package com.banmatrip.guardian.dto.response.permission;

import lombok.Data;

/**
 * @author jepson
 * @Description: 功能权限实体
 * @create 2017-09-20 19:53
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class FunctionPermission {

     /**URL**/
     String url;
     /**功能类型**/
     String functionType;
     /*功能名称*/
     String name;

}