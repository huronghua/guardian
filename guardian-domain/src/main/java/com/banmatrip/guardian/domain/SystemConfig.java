package com.banmatrip.guardian.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Miracle Xu
 * @Description: SystemConfig实体类
 * @create 2017-12-13 14:06
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class SystemConfig {
    private Integer id;

    private String name;

    private String url;

    private Integer sort;

    private String createTime;

    private String updateTime;

}
