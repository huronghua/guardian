package com.banmatrip.guardian.repository.mapper.rolepermission;

import com.banmatrip.guardian.domain.RoleFunction;
import com.banmatrip.guardian.domain.RoleFunctionKey;
import com.banmatrip.guardian.vo.role.RoleFunctionVo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface RoleFunctionMapper {
    int deleteByPrimaryKey(RoleFunctionKey key);

    int insert(RoleFunction record);

    int insertSelective(RoleFunction record);

    RoleFunction selectByPrimaryKey(RoleFunctionKey key);

    int updateByPrimaryKeySelective(RoleFunction record);

    int updateByPrimaryKey(RoleFunction record);

    List<RoleFunctionVo> getRoleFunction(Integer roleId);

    List<LinkedHashMap<String,Object>> getParentFunctionType();

    List<LinkedHashMap<String,Object>> getChildFunctionType(Integer parentType);

    List<LinkedHashMap<String,Object>> getChildFunctionList(Map<String,Object> paramMap);

    List<LinkedHashMap<String,Object>> getParentFunctionList(Map<String,Object> paramMap);

    void deleteAllRoleFunctionByRoleId(Integer roleId);

    int updateRoleFunctionByRoleId(Map<String,Object> paramMap);
}