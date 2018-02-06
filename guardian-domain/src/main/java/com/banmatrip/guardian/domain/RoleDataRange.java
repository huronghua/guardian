package com.banmatrip.guardian.domain;

import java.util.Date;

public class RoleDataRange {
    private Integer id;

    private Integer roleId;

    private Integer dataRangeType;

    private Integer dataRangeId;

    private Date createTime;

    private Date updateTime;

    private Integer createId;

    private Integer updateId;

    public RoleDataRange(Integer id, Integer roleId, Integer dataRangeType, Integer dataRangeId, Date createTime, Date updateTime, Integer createId, Integer updateId, Integer deleteFlag) {
        this.id = id;
        this.roleId = roleId;
        this.dataRangeType = dataRangeType;
        this.dataRangeId = dataRangeId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createId = createId;
        this.updateId = updateId;
    }

    public RoleDataRange() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDataRangeType() {
        return dataRangeType;
    }

    public void setDataRangeType(Integer dataRangeType) {
        this.dataRangeType = dataRangeType;
    }

    public Integer getDataRangeId() {
        return dataRangeId;
    }

    public void setDataRangeId(Integer dataRangeId) {
        this.dataRangeId = dataRangeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

}