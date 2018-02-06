package com.banmatrip.guardian.dto.response.base;

import lombok.Data;

/**
 * create by jepson on 2017/09/12
 */
@Data
public class ArgumentInvalidResult {
    private String field;
    private Object rejectedValue;
    private String defaultMessage;
}