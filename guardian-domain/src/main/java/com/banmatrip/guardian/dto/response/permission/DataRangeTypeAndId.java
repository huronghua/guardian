package com.banmatrip.guardian.dto.response.permission;

import lombok.Data;

/**
 * @author jepson
 * @Description: 数据类型和ID
 * @create 2017-09-23 18:04
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class DataRangeTypeAndId {

    /**数据范围类型**/
    private String dataRangeType;
    /**数据范围ID**/
    private String dataRangeId;
}