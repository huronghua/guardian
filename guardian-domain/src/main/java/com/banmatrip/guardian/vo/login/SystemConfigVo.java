package com.banmatrip.guardian.vo.login;

import lombok.Data;

/**
 * @author jepson
 * @Description: 系统配置VO
 * @create 2017-11-29 13:53
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class SystemConfigVo {
    /**系统配置id**/
    private Long id;
    /**系统配置名称**/
    private String name;
    /**系统配置url**/
    private String url;
    /**排序**/
    private Integer sort;
}