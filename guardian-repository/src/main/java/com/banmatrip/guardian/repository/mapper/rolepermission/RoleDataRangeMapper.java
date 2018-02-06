package com.banmatrip.guardian.repository.mapper.rolepermission;


import com.banmatrip.guardian.domain.RoleDataRange;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface RoleDataRangeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleDataRange record);

    int insertSelective(RoleDataRange record);

    RoleDataRange selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleDataRange record);

    int updateByPrimaryKey(RoleDataRange record);

    void deleteByRoleId(Integer roleId);

    void insertRoleDataRange(@Param("param") Map<String,Object> param);
}