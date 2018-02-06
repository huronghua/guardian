package com.banmatrip.guardian.vo.role;

import lombok.Data;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-09-19 12:30
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class RoleFunctionVo {
    private Integer functionId;
    private Integer roleId;
    private Integer functionType;
    private String functionName;
    private Integer functionSubType;
    private String typeName;
    private String subTypeName;
}