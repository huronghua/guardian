package com.banmatrip.guardian.core.enums;

/**
 * 数据范围类型
 */
public enum DataRangeTypeEnum {
    DESINATION_TYPE(1,"目的地"),
    PRODUCT_TYPE(2,"产品类型"),
    PLATFORM_TYPE(3,"渠道"),
    RESOURCE_TYPE(4,"资源类型");

    // 成员变量
    private int type;
    private String name;

    /**
     * 构造方法
     *
     * @param type
     * @param name
     */
    DataRangeTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }


    /**
     * @return 返回成员变量值
     */
    public int getType() {
        return type;
    }

    /**
     * @return 返回成员变量值
     */
    public String getName() {
        return name;
    }
}
