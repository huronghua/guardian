package com.banmatrip.guardian.controller;

import com.banmatrip.guardian.common.PermissionRender;
import com.banmatrip.guardian.domain.RoleGroup;
import com.banmatrip.guardian.interfaces.role.RoleGroupService;
import com.banmatrip.guardian.interfaces.role.RoleService;
import com.banmatrip.guardian.vo.role.RoleGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by banma on 2017/9/15.
 */
@Controller
@RequestMapping(value = "/role_group")
public class RoleGroupController {

    @Autowired
    private RoleGroupService roleGroupService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionRender permissionRender;


    @RequestMapping(value = "/index")
    public String roleGroupIndex(ModelMap modelMap,Principal principal){
        List<RoleGroup> roleGroupList = roleGroupService.selectRoleGroupList();
        modelMap.put("roleGroupList",roleGroupList);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "role/role_group_index";
    }


    /**
     * 新增或者编辑角色组
     * @param request
     * @param response
     * @param modelMap
     * @param principal
     * @throws IOException
     */
    @RequestMapping(value = "/addRoleGroup")
    public void addFunction(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Principal principal) throws IOException {
        String roleGroupName = request.getParameter("roleGroupName");
        RoleGroup roleGroup = new RoleGroup();
        roleGroup.setName(roleGroupName);
        if(request.getParameter("role_group_id").equals("")) {
            Integer roleGroupCount = roleGroupService.findRoleGroupCount(roleGroupName);
            if (roleGroupCount.intValue()==0) {
                roleGroupService.saveRoleGroup(roleGroup);
            }
        }else {
            Integer roleGroupId = Integer.valueOf(request.getParameter("role_group_id"));
            roleGroup.setId(roleGroupId);
            Integer roleGroupCount = roleGroupService.findRoleGroupCount(roleGroupName);
            if (roleGroupCount.intValue()==0) {
                roleGroupService.updateRoleGroupById(roleGroup);
            }
        }
        response.sendRedirect("/role_group/index");
    }

    @RequestMapping(value = "/deleteRoleGroup")
    public void deleteFunction(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,Principal principal) throws IOException {
        Integer roleGroupId = Integer.valueOf(request.getParameter("roleGroupId"));
        roleGroupService.deleteRoleGroupById(roleGroupId);
        response.sendRedirect("/role_group/index");
    }


    @RequestMapping(value = "/save")
    public String saveRoleGroup(ModelMap modelMap, RoleGroupVo roleGroupVo, Principal principal) {

        List<Map<String, Object>> tree = roleService.initRoleTree();
        Integer groupId=roleGroupService.selectIdByName(roleGroupVo.getRoleGroupName());
        modelMap.put("tree", tree);
        modelMap.put("groupId",groupId);
        /**成员架构功能权限**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "role/index";
    }


    @RequestMapping(value = "/role_group_list" ,method = RequestMethod.GET)
    @ResponseBody
    public List<RoleGroup> selectRoleGroupList(){
        List<RoleGroup> roleGroupList=roleGroupService.selectRoleGroupList();
        return roleGroupList;
    }

}
