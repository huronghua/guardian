package com.banmatrip.guardian.repository.mapper.rolepermission;

import com.banmatrip.guardian.domain.Role;
import com.banmatrip.guardian.vo.role.RoleMemberVo;
import com.banmatrip.guardian.vo.role.RoleVo;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    int updateDataRange(RoleVo roleVo);

    int addRole(RoleVo roleVo);

    int updateRole(RoleVo roleVo);

    List<Role> findRolesByGroupId(Integer groupId);

    List<RoleMemberVo> findUserByRoleId(Integer roleId);

    List<Role> findAll();

    List<Role> findAllForCheck();

    Integer findRoleIdByName(RoleVo roleVo);

    Integer findRoleCount(RoleVo roleVo);

    List<Map<String,Object>> selectMemberRoleById(List<Integer> memberIdList);

    void insertOneUserRoleByUserList(Map<String, Object> adminAccountTtmp);

    List<Integer> selectRoleListNow(int id);

    void deleteChargeRole(Map<String, Object> chargeRole);

    void insertChargeRole(Map<String, Object> chargeRole);

    List<Integer> findAllForUser();

    List<Integer> findUserAdminIdByRoleId(Integer roleId);

    Integer selectAdminIdByUserId(Integer userId);
}