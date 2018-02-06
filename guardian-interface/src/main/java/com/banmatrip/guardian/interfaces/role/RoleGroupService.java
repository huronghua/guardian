package com.banmatrip.guardian.interfaces.role;

import com.banmatrip.guardian.domain.RoleGroup;
import com.banmatrip.guardian.vo.role.RoleGroupVo;


import java.util.List;

/**
 * Created by banma on 2017/9/18.
 */
public interface RoleGroupService {

    public void saveRoleGroup(RoleGroup roleGroup);

    public List<RoleGroup> selectRoleGroupList();

    public Integer findRoleGroupCount(String name);

    public Integer selectIdByName(String name);

    public Integer deleteRoleGroupById(Integer roleGroupId);

    public Integer updateRoleGroupById(RoleGroup roleGroup);
}
