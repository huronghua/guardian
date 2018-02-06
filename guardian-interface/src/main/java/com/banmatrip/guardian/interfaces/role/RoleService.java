package com.banmatrip.guardian.interfaces.role;

import com.banmatrip.guardian.domain.Role;
import com.banmatrip.guardian.vo.role.RoleMemberVo;
import com.banmatrip.guardian.vo.role.RoleVo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface RoleService {
    /*获取角色组Json树*/
    public List<Map<String,Object>> roleJsonTree();

    public List<RoleMemberVo> getRoleMember(Integer roleId);

    public List<RoleMemberVo> getMemberWithoutRole();

    public Integer getMemberCountWithoutRole();

    public int addRoleMember(RoleMemberVo roleMemberVo);

    public List<Map<String,Object>> initRoleTree();

    public List<Role> findAll();

    public List<Map<String,Object>> findAllDep(int userId);

    public List<Map<String,Object>> findAllDep();

	public int updateDataRange(RoleVo roleVo);

    public void deleteRoleMember(RoleMemberVo roleMemberVo);

	public void saveRole(RoleVo roleVo);

    public Role findRoleById(Integer id);
	
    public List<RoleMemberVo> ajaxSearch(String textSearch,Integer roleId);

    public List<LinkedHashMap<String,Object>> getRoleFunction(Integer roleId);

    public int updateRoleFunction(List<String> functionList,Integer roleId);

    /*public void insertRoleDataRange(RoleVo roleVo);*/

    public Integer findRoleIdByName(RoleVo roleVo);

    public Integer findRoleCount(RoleVo roleVo);

    List<Map<String,Object>> downloadRoleMemberInfo();

    public void deleteAllRoleFunctionByRoleId(Integer roleId);

    /*根据角色ID查找该角色下所有用户的adminId*/
    public List<Integer> findUserAdminIdByRoleId(Integer roleId);

    /*根据userId查询orange系统的adminId*/
    public Integer selectAdminIdByUserId(Integer userId);
}
