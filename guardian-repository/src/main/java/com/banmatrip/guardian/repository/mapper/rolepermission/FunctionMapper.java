package com.banmatrip.guardian.repository.mapper.rolepermission;

import com.banmatrip.guardian.domain.Function;
import com.banmatrip.guardian.dto.response.permission.DataRangePermisssion;
import com.banmatrip.guardian.dto.response.permission.FunctionPermission;

import java.util.List;
import java.util.Map;

public interface FunctionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Function record);

    int insertSelective(Function record);

    Function selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Function record);

    int updateByPrimaryKey(Function record);

    List<Map<String,Object>> getAllFunction();

    List<Map<String,Object>> getFunctionTypeList();

    List<Map<String, Object>> searchFunctionByName(String searchtext);

}