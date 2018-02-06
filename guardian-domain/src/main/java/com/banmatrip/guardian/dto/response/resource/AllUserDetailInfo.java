package com.banmatrip.guardian.dto.response.resource;

import lombok.Data;

import java.util.Map;

/**
 * @author Miracle Xu
 * @Description:
 * @create 2018-01-16 13:47
 * @Copyright: 2018 www.banmatrip.com All rights reserved.
 **/
@Data
public class AllUserDetailInfo {
    /*人员id*/
    private Map<Integer,UserDetailInfo> userDetailInfo;
}
