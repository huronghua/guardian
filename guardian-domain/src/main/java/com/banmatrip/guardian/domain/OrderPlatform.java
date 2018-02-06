package com.banmatrip.guardian.domain;

import java.util.Date;

public class OrderPlatform {
    private Integer id;

    private String name;

    private Byte delete_flag;

    private Date record_time;

    private Byte show_flag;

    private Integer parent_platform_id;

    private Integer level;

    private String remark;

    public OrderPlatform(Integer id, String name, Byte delete_flag, Date record_time, Byte show_flag, Integer parent_platform_id, Integer level, String remark) {
        this.id = id;
        this.name = name;
        this.delete_flag = delete_flag;
        this.record_time = record_time;
        this.show_flag = show_flag;
        this.parent_platform_id = parent_platform_id;
        this.level = level;
        this.remark = remark;
    }

    public OrderPlatform() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Byte delete_flag) {
        this.delete_flag = delete_flag;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    public Byte getShow_flag() {
        return show_flag;
    }

    public void setShow_flag(Byte show_flag) {
        this.show_flag = show_flag;
    }

    public Integer getParent_platform_id() {
        return parent_platform_id;
    }

    public void setParent_platform_id(Integer parent_platform_id) {
        this.parent_platform_id = parent_platform_id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}