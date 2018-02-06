package com.banmatrip.guardian.service.role;

import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.Role;
import com.banmatrip.guardian.domain.RoleGroup;
import com.banmatrip.guardian.domain.User;
import com.banmatrip.guardian.domain.UserRole;
import com.banmatrip.guardian.interfaces.DepartmentService;
import com.banmatrip.guardian.interfaces.role.RoleService;
import com.banmatrip.guardian.repository.mapper.membership.DepartmentMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserRoleMapper;
import com.banmatrip.guardian.repository.mapper.rolepermission.*;
import com.banmatrip.guardian.vo.role.RoleMemberVo;
import com.banmatrip.guardian.vo.role.RoleVo;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleGroupMapper roleGroupMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    DictionaryMapper dictionaryMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleFunctionMapper roleFunctionMapper;

    @Autowired
    RoleDataRangeMapper roleDataRangeMapper;

    @Autowired
    DepartmentService departmentService;


    @Override
    public List<Map<String, Object>> roleJsonTree() {
        List<Map<String, Object>> jsonTree = new ArrayList<>();
        List<RoleGroup> roleGroups = roleGroupMapper.findAll();
        Map<String, Object> state = new HashMap<>();
        state.put("disabled", false);
        state.put("opened", true);
        /*定义最上层的父节点*/
        Map<String, Object> root = new HashMap<>();
        root.put("id", "斑马旅游");
        root.put("text", "斑马旅游");
        root.put("parent", "#");
        root.put("state", state);
        jsonTree.add(root);
        if (CollectionUtils.isNotEmpty(roleGroups)) {
            for (RoleGroup roleGroup : roleGroups) {
                Map<String, Object> parent = new HashMap<>();
                /*定义二级节点*/
                parent.put("id", roleGroup.getName());
                parent.put("text", roleGroup.getName());
                parent.put("group_id", roleGroup.getId());
                parent.put("state", state);
                parent.put("parent", "斑马旅游");
                jsonTree.add(parent);
                List<Role> roleList = roleMapper.findRolesByGroupId(roleGroup.getId());
                if (CollectionUtils.isNotEmpty(roleList)) {
                    for (Role role : roleList) {
                        Map<String, Object> child = new HashMap<>();
                        child.put("id", role.getName());
                        child.put("text", role.getName());
                        child.put("parent", roleGroup.getName());
                        child.put("role_id", role.getId());
                        child.put("state", state);
                        child.put("icon", "glyphicon glyphicon-user");
                        jsonTree.add(child);
                    }
                }
            }
        }

        return jsonTree;
    }

    @Override
    public List<RoleMemberVo> getRoleMember(Integer roleId) {
        return userMapper.findUserByRoleId(roleId);
    }

    @Override
    public List<RoleMemberVo> getMemberWithoutRole() {
        return userMapper.getMemberWithoutRole();
    }

    @Override
    public Integer getMemberCountWithoutRole() {
        return userMapper.getMemberCountWithoutRole();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED, timeout = 60)
    public int addRoleMember(RoleMemberVo roleMemberVo) {
        try {
            UserRole userRole = new UserRole();
            userRole.setUserId(roleMemberVo.getId());
            userRole.setRoleId(roleMemberVo.getRoleId());
            return userRoleMapper.addRoleMember(userRole);
        } catch (Exception e) {
            throw new RuntimeException("增加角色成员异常!" + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> initRoleTree() {
        List<Map<String, Object>> jsonTree = new ArrayList<>();
        List<RoleGroup> roleGroups = roleGroupMapper.findAll();
        if (CollectionUtils.isNotEmpty(roleGroups)) {
            for (RoleGroup roleGroup : roleGroups) {
                Map<String, Object> parent = new HashMap<>();
                parent.put("group_id", roleGroup.getId());
                parent.put("name", roleGroup.getName());
                List<Role> roleList = roleMapper.findRolesByGroupId(roleGroup.getId());
                if (CollectionUtils.isNotEmpty(roleList)) {
                    List<Map<String, Object>> childList = new ArrayList<>();
                    for (Role role : roleList) {
                        Map<String, Object> child = new HashMap<>();
                        child.put("role_id", role.getId());
                        child.put("name", role.getName());
                        childList.add(child);
                    }
                    parent.put("children", childList);
                }
                jsonTree.add(parent);
            }
        }
        return jsonTree;
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public List<Map<String, Object>> findAllDep(int userId) {
        List<Map<String, Object>> departmentMapperAll = departmentMapper.findAll();
        List<Map<String, Object>> realDep = new ArrayList<>();
        for (Map dep : departmentMapperAll) {
            List<Map<String, Object>> temp = departmentMapper.findDepChild((Integer) dep.get("id"));
            List<Integer> depTrue = userMapper.selectDepById(userId);
            List<Map<String, Object>> child = new ArrayList<>();
            Map map = new HashMap();
            map.put("name", dep.get("name"));
            map.put("id", dep.get("id"));
            if (depTrue.contains(dep.get("id"))) {
                map.put("isTrue", "true");
            } else {
                map.put("isTrue", "false");
            }
            if (null != temp) {
                for (Map depMap : temp) {
                    if (depTrue.contains(depMap.get("id"))) {
                        depMap.put("isTrue", "true");
                        child.add(depMap);
                    } else {
                        depMap.put("isTrue", "false");
                        child.add(depMap);
                    }
                }
                map.put("children", child);
                realDep.add(map);
            }
        }
        return realDep;
    }

    @Override
    public List<Map<String, Object>> findAllDep() {
        List<Map<String, Object>> departmentMapperAll = departmentMapper.findAll();
        List<Map<String, Object>> realDep = new ArrayList<>();
        for (Map dep : departmentMapperAll) {
            List<Map<String, Object>> temp = departmentMapper.findDepChild((Integer) dep.get("id"));
            Map map = new HashMap();
            map.put("name", dep.get("name"));
            map.put("id", dep.get("id"));
            if (null != temp) {
                map.put("children", temp);
                realDep.add(map);
            }
        }
        return realDep;
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class, timeout = 60)
    public void deleteRoleMember(RoleMemberVo roleMemberVo) {
        try {
            UserRole userRole = new UserRole();
            userRole.setUserId(roleMemberVo.getId());
            userRole.setRoleId(roleMemberVo.getRoleId());
            userRoleMapper.deleteRoleMemberByUpdateDeleteFlag(userRole);
        } catch (Exception e) {
            throw new RuntimeException("删除角色成员失败" + e.getMessage());
        }
    }

    @Override
    public List<RoleMemberVo> ajaxSearch(String textSearch, Integer roleId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("textSearch", textSearch);
        map.put("roleId", roleId);
        return userMapper.ajaxSearch(map);
    }

    @Override

    public int updateDataRange(RoleVo roleVo){
        int count=roleMapper.updateDataRange(roleVo);
        return count;
    }


    @Override
    public void saveRole(RoleVo roleVo) {
        if (roleVo.getRoleId() != null) {
            int count = roleMapper.updateRole(roleVo);
        } else {
            int count = roleMapper.addRole(roleVo);
        }
    }

    @Override
    public Role findRoleById(Integer id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        return role;
    }

    @Override
    public List<LinkedHashMap<String, Object>> getRoleFunction(Integer roleId) {
        List<LinkedHashMap<String, Object>> mapList = new ArrayList<>();
        List<LinkedHashMap<String, Object>> parentFunctionType = roleFunctionMapper.getParentFunctionType();
        for (LinkedHashMap<String, Object> parentFunction : parentFunctionType) {
            LinkedHashMap<String, Object> parentMap = new LinkedHashMap<>();
            Integer parentType = Integer.valueOf((String) parentFunction.get("type"));
            String parentName = (String) parentFunction.get("name");
            parentMap.put("functionType", parentType);
            parentMap.put("typeName", parentName);
            List<LinkedHashMap<String, Object>> childtFunctionType = roleFunctionMapper.getChildFunctionType(parentType);
            if (CollectionUtils.isNotEmpty(childtFunctionType)) {
                List<LinkedHashMap<String, Object>> childFunctionList = new ArrayList<>();
                for (LinkedHashMap<String, Object> childFunction : childtFunctionType) {
                    LinkedHashMap<String, Object> childMap = new LinkedHashMap<>();
                    Integer childType = Integer.valueOf((String) childFunction.get("sub_type"));
                    String childName = (String) childFunction.get("name");
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("roleId", roleId);
                    paramMap.put("childType", childType);
                    List<LinkedHashMap<String, Object>> subFunctionList = roleFunctionMapper.getChildFunctionList(paramMap);
                    childMap.put("functionSubType", childType);
                    childMap.put("subTypeName", childName);
                    childMap.put("roleFunction", subFunctionList);
                    childFunctionList.add(childMap);
                }
                parentMap.put("subFunction", childFunctionList);
            } else {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("roleId", roleId);
                paramMap.put("parentType", parentType);
                List<LinkedHashMap<String, Object>> parentFunctionList = roleFunctionMapper.getParentFunctionList(paramMap);
                parentMap.put("roleFunction", parentFunctionList);
            }
            mapList.add(parentMap);
        }
        return mapList;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 60, rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public int updateRoleFunction(List<String> functionList, Integer roleId) {
        roleFunctionMapper.deleteAllRoleFunctionByRoleId(roleId);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("functionList", functionList);
        paramMap.put("roleId", roleId);
        int result = roleFunctionMapper.updateRoleFunctionByRoleId(paramMap);
        return result;
    }

    @Override
    public List<Map<String,Object>> downloadRoleMemberInfo() {
        return userRoleMapper.downloadRoleMemberInfo();
    }

    /*插入角色数据范围*//*
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 60, rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void insertRoleDataRange(RoleVo roleVo){
        Integer roleId=roleVo.getRoleId();
        roleDataRangeMapper.deleteByRoleId(roleId);
        *//*插入目的地数据范围*//*
        insertRoleDataRangeByType(roleId,1,roleVo.getDestinationType());
        *//*插入渠道数据范围*//*
        insertRoleDataRangeByType(roleId,2,roleVo.getPlatformType());
        *//*插入产品类型数据范围*//*
        insertRoleDataRangeByType(roleId,3,roleVo.getProductType());
        *//*插入资源类型数据范围*//*
        insertRoleDataRangeByType(roleId,4,roleVo.getResourceType());
    }


    *//*
     * @function:插入角色数据范围数据
     * @param：roleId角色id,dataRangeType 数据范围类型,relateType 数据范围类型关联数据方式
     * @author：zhangwei
     * *//*
    public void insertRoleDataRangeByType(Integer roleId, Integer dataRangeType, Integer relateType) {
        List<Integer> list=new ArrayList();
        switch (relateType) {
              *//*跟人相关*//*
            case 1:
                list = userMapper.findUserIdByRoleId(roleId);
                break;
            *//*跟部门相关*//*
            case 2:
                list = userMapper.findDepartmentIdByRoleId(roleId);
                break;
            *//*跟部门及下属部门相关*//*
            case 3:
                list =findSubDepartment(roleId);
                break;
            default:
                System.out.print("最大权限");
        }
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("roleId",roleId);
        param.put("dataRangeType",dataRangeType);
        param.put("relateType",relateType);
        param.put("list",list);
        if (CollectionUtils.isNotEmpty(list)) {
            roleDataRangeMapper.insertRoleDataRange(param);
        }
    }


    *//*查询部门及其下属部门*//*
    public List<Integer> findSubDepartment(Integer roleId){
        Set<Integer> set=new HashSet();
        List<Integer> departmentIdList = userMapper.findDepartmentIdByRoleId(roleId);
        for (Integer departmentId:
             departmentIdList) {
            List tree=new ArrayList();
            List<Department> departmentList=departmentService.findChildDepartment(departmentId,tree);
            set.add(departmentId);
            for (Department department:departmentList
                 ) {
                set.add(department.getId());
            }
        }
        List<Integer> result = new ArrayList<>(set);
        return result;
    }*/

    /*根据角色名字和角色组获取id*/
    public Integer findRoleIdByName(RoleVo roleVo){
        Integer roleId=roleMapper.findRoleIdByName(roleVo);
        return roleId;
    }

    /*查询角色组内是否有该角色*/
    public Integer findRoleCount(RoleVo roleVo){
        Integer roleCount=roleMapper.findRoleCount(roleVo);
        return roleCount;
    }

    @Override
    public void deleteAllRoleFunctionByRoleId(Integer roleId) {
        roleFunctionMapper.deleteAllRoleFunctionByRoleId(roleId);
    }

    @Override
    public List<Integer> findUserAdminIdByRoleId(Integer roleId) {
        return roleMapper.findUserAdminIdByRoleId(roleId);
    }

    @Override
    public Integer selectAdminIdByUserId(Integer userId) {
        return roleMapper.selectAdminIdByUserId(userId);
    }
}
