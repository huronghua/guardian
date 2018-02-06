package com.banmatrip.guardian.domain;

import java.util.Date;

public class UserDataRange {
    private Integer id;

    private Integer userId;

    private String dataRangeType;

    private Integer dataRangeId;

    private Date createTime;

    private Date updateTime;

    private Integer creatId;

    private Integer updateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDataRangeType() {
        return dataRangeType;
    }

    public void setDataRangeType(String dataRangeType) {
        this.dataRangeType = dataRangeType == null ? null : dataRangeType.trim();
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

    public Integer getCreatId() {
        return creatId;
    }

    public void setCreatId(Integer creatId) {
        this.creatId = creatId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
}