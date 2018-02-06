package com.banmatrip.guardian.repository.mapper.common;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface BaseMapper {
    List<Map<String, Object>> getDictionaryData(@Param(value = "type") Integer type);
}
