package com.banmatrip.guardian.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Miracle Xu
 * @Description:部门组实体类
 * @create 2017-09-18 17:46
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Data
public class DepartmentGroup {

    private Integer id;

    private Integer deprtmentId;

    private Date createTime;

    private Integer createId;

    private Integer deleteFlag;
}
