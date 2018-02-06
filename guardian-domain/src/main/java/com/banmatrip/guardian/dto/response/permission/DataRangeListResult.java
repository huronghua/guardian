package com.banmatrip.guardian.dto.response.permission;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author jepson
 * @Description:
 * @create 2017-09-23 19:08
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class DataRangeListResult {

    /**目的地**/
    HashSet<String> destinationList = new HashSet<String>();
    /**平台**/
    HashSet<String> platformList = new HashSet<String>();
    /**产品**/
    HashSet<String> productList = new HashSet<String>();
    /**资源**/
    HashSet<String> resourceList = new HashSet<String>();
}