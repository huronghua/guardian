package com.banmatrip.guardian.core.enums;

/**
 *  机构类型枚举
 */
public enum OrganizeTypeEnum {

    ADMIN_TYPE(0, "admin"),
    SELF_TYPE(1, "本人相关"),
    SELF_DEPT_TYPE(2, "本部门相关"),
    SELF_DEPT_AND_SUB_DEPT(3, "本部门及下属部门");

    /**
     * 构造函数
     *
     * @param type
     * @param name
     */
    OrganizeTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    /**
     * 类型
     */
    private int type;

    /**
     * 名称
     */
    private String name;
}
