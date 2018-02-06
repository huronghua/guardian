package com.banmatrip.guardian.domain;

import java.util.Date;

public class ProductOrderTag {
    private Integer id;

    private String name;

    private Byte type;

    private Date record_time;

    private Byte delete_flag;

    public ProductOrderTag(Integer id, String name, Byte type, Date record_time, Byte delete_flag) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.record_time = record_time;
        this.delete_flag = delete_flag;
    }

    public ProductOrderTag() {
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    public Byte getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Byte delete_flag) {
        this.delete_flag = delete_flag;
    }
}