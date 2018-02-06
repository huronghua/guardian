package com.banmatrip.guardian.repository.mapper.rolepermission;

import com.banmatrip.guardian.domain.RoleGroup;
import com.banmatrip.guardian.vo.role.RoleGroupVo;

import java.util.List;

public interface RoleGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleGroup record);

    int insertSelective(RoleGroup record);

    RoleGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleGroup record);

    int updateByPrimaryKey(RoleGroup record);

    List<RoleGroup> selectRoleGroupList();

    int saveRoleGroup(RoleGroup roleGroup);

    List<RoleGroup> findAll();

    Integer findRoleGroupCount(String name);

    Integer selectIdByName(String name);
}