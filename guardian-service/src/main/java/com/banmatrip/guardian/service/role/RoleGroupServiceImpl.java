package com.banmatrip.guardian.service.role;

import com.banmatrip.guardian.domain.RoleGroup;
import com.banmatrip.guardian.interfaces.role.RoleGroupService;
import com.banmatrip.guardian.repository.mapper.rolepermission.RoleGroupMapper;
import com.banmatrip.guardian.vo.role.RoleGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by banma on 2017/9/18.
 */
@Service
public class RoleGroupServiceImpl implements RoleGroupService {
    @Autowired
    private RoleGroupMapper roleGroupMapper;


    @Override
    public void saveRoleGroup(RoleGroup roleGroup){
        int count=roleGroupMapper.saveRoleGroup(roleGroup);
    }


    @Override
    public List<RoleGroup> selectRoleGroupList(){
        List<RoleGroup> roleGroupList=roleGroupMapper.selectRoleGroupList();
        return roleGroupList;
    }

    @Override
    public Integer findRoleGroupCount(String name){
        Integer roleGroupCount=roleGroupMapper.findRoleGroupCount(name);
        return roleGroupCount;
    }

    @Override
    public Integer selectIdByName(String name){
        Integer groupId=roleGroupMapper.selectIdByName(name);
        return groupId;
    }

    @Override
    public Integer deleteRoleGroupById(Integer roleGroupId) {
        return roleGroupMapper.deleteByPrimaryKey(roleGroupId);
    }

    @Override
    public Integer updateRoleGroupById(RoleGroup roleGroup) {
        return roleGroupMapper.updateByPrimaryKeySelective(roleGroup);
    }
}
