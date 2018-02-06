package com.banmatrip.guardian.repository.mapper.membership;

import com.banmatrip.guardian.domain.Role;
import com.banmatrip.guardian.domain.UserRole;
import com.banmatrip.guardian.domain.UserRoleKey;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserRoleMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(UserRoleKey key);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    List<Role> selectByUserId(String UserId);

    int deleteRoleMemberByUpdateDeleteFlag(UserRole userRole);

    int addRoleMember(UserRole userRole);

    List<Role> selectRoleByUserId(Integer userId);

    List<Map<String,Object>> downloadRoleMemberInfo();

}