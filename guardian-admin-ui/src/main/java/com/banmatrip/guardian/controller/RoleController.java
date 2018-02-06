package com.banmatrip.guardian.controller;

import com.banmatrip.guardian.excel.DownloadNotByTemplate;
import com.banmatrip.guardian.excel.ExcelHeadAssemble;
import com.banmatrip.guardian.common.PermissionRender;
import com.banmatrip.guardian.domain.Role;
import com.banmatrip.guardian.excel.FileUtils;
import com.banmatrip.guardian.interfaces.Base.BaseService;
import com.banmatrip.guardian.interfaces.Base.MembercacheService;
import com.banmatrip.guardian.interfaces.role.RoleGroupService;
import com.banmatrip.guardian.interfaces.role.RoleService;
import com.banmatrip.guardian.vo.role.RoleMemberVo;
import com.banmatrip.guardian.vo.role.RoleVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private RoleGroupService roleGroupService;

    @Autowired
    private PermissionRender permissionRender;

    @Autowired
    private MembercacheService membercacheService;

    Gson gson = new GsonBuilder().serializeNulls().create();

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String initPage(ModelMap modelMap,Principal principal){
        List<Map<String, Object>> tree = roleService.initRoleTree();
        Integer id=0;
        String title="";
        if (CollectionUtils.isNotEmpty(tree)&&tree.get(0).get("children")!=null) {
            List<Map<String, Object>> childList = (List<Map<String, Object>>) tree.get(0).get("children");
            id = (Integer) childList.get(0).get("role_id");
            title = String.valueOf(childList.get(0).get("name"));
        }
        modelMap.put("roleId",id);
        modelMap.put("title",title);
        modelMap.put("tree",tree);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "role/index";
    }

    @RequestMapping(value="/jsontree",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getJsonTree(){
        List<Map<String,Object>> mapList =  roleService.roleJsonTree();
        return mapList;
    }

    @RequestMapping(value="/getRoleMember",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getRoleMember(@RequestParam Integer roleId) throws Exception{
        Map<String,Object> data=new HashMap<String,Object>();
        List<RoleMemberVo> memberVoList = roleService.getRoleMember(roleId);
        Role role=roleService.findRoleById(roleId);
        /*List<RoleGroup> roleGroupList=roleGroupService.selectRoleGroupList();*/
        List<LinkedHashMap<String, Object>> functionVoList =  roleService.getRoleFunction(roleId);
        /*data.put("roleGroupList",roleGroupList);*/
        Integer countWithoutRole = roleService.getMemberCountWithoutRole();
        data.put("countWithoutRole",countWithoutRole);
        data.put("memberVoList",memberVoList);
        data.put("functionVoList",functionVoList);
        data.put("role",role);
        data.put("roleId",roleId);
        return data;
    }

    @RequestMapping(value="/getMemberWithoutRole",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMemberWithoutRole() throws Exception{
        Map<String,Object> data=new HashMap<String,Object>();
        List<RoleMemberVo> memberVoList = roleService.getMemberWithoutRole();
        data.put("memberVoList",memberVoList);
        return data;
    }

    /*下载角色成员信息*/
    @RequestMapping(value = "/downloadRoleMemberInfo")
    public void downloadRoleMemberInfo(HttpServletResponse response) throws Exception {
        List<Map<String, Object>> result = roleService.downloadRoleMemberInfo();
        List<String> resultLine = new ArrayList();
        if(result.size()>0){
            Map<String, Object> head = result.get(0);
            resultLine = ExcelHeadAssemble.alertOrderDetailHeadAssemble(head);
        }
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        /*返回结果*/
        String fileName = FileUtils.fileNameUtil(simpleDateFormat.format(now) + "系统角色成员列表",".xls");//文件名
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", fileName);
        DownloadNotByTemplate.getInstance().exportObjectsToExcel(result,resultLine,false,response.getOutputStream());
        response.getOutputStream().close();
    }



    @RequestMapping(value="/getDictionaryData",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getDictionaryData(@RequestParam Integer type){
        return baseService.getDictionaryData(type);
    }

    @RequestMapping(value="/addRoleMember",method = RequestMethod.GET)
    public String addRoleMember(ModelMap modelMap,RoleMemberVo roleMemberVo,Principal principal){
        roleService.addRoleMember(roleMemberVo);
        /*根据userId查询出orange系统的adminId*/
        Integer adminId = roleService.selectAdminIdByUserId(roleMemberVo.getId());
        List<Integer> adminIdList = new ArrayList<>();
        adminIdList.add(adminId);
        /*插入orange系统的memberchache中通知权限变更*/
        membercacheService.notifyPermissionChangeByAdminId(adminIdList);

        List<Map<String, Object>> tree = roleService.initRoleTree();
        modelMap.put("roleId",roleMemberVo.getRoleId());
        modelMap.put("tree",tree);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "role/index";
    }

    @RequestMapping(value="/deleteRoleMember",method = RequestMethod.GET)
    public String deleteRoleMember(ModelMap modelMap,RoleMemberVo roleMemberVo,Principal principal){
        roleService.deleteRoleMember(roleMemberVo);
        /*根据userId查询出orange系统的adminId*/
        Integer adminId = roleService.selectAdminIdByUserId(roleMemberVo.getId());
        List<Integer> adminIdList = new ArrayList<>();
        adminIdList.add(adminId);
        /*插入orange系统的memberchache中通知权限变更*/
        membercacheService.notifyPermissionChangeByAdminId(adminIdList);

        List<Map<String, Object>> tree = roleService.initRoleTree();
        modelMap.put("roleId",roleMemberVo.getRoleId());
        modelMap.put("tree",tree);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "role/index";
    }

    @RequestMapping(value="/ajaxSearch",method = RequestMethod.GET)
    @ResponseBody
    public List<RoleMemberVo> ajaxSearch(RoleMemberVo roleMemberVo){
        String textSearch = roleMemberVo.getTextSearch();
        Integer roleId = roleMemberVo.getRoleId();
        return roleService.ajaxSearch(textSearch,roleId);
    }

	@RequestMapping(value = "/saveRoleData",method = RequestMethod.POST)
    public String dateRange(ModelMap modelMap,RoleVo roleVo,@RequestParam String roleFunction,Principal principal,@RequestParam String tab_index) throws Exception{
        List<Map<String, Object>> tree = roleService.initRoleTree();
        /*保存数据范围类型*/
        roleService.updateDataRange(roleVo);

        /*插入角色数据范围*/
        /*roleService.insertRoleDataRange(roleVo);*/

        /*将人员权限的变动存入membercache中，key为update_power_cache_{user_id},value为1*/
        List<Integer> permissionChangedAdminIdList = roleService.findUserAdminIdByRoleId(roleVo.getRoleId());
            membercacheService.notifyPermissionChangeByAdminId(permissionChangedAdminIdList);

        if(!StringUtils.isEmpty(roleFunction)){
            String[] array = roleFunction.split(",");
            List<String> functionList = Arrays.asList(array);
            roleService.updateRoleFunction(functionList,roleVo.getRoleId());
        }else if ("".equals(roleFunction)){
            roleService.deleteAllRoleFunctionByRoleId(roleVo.getRoleId());
        }
        modelMap.put("roleId",roleVo.getRoleId());
        modelMap.put("tree",tree);
        modelMap.put("tab_index",tab_index);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "role/index";
    }

    @RequestMapping("/save")
    public String saveRole(ModelMap modelMap, RoleVo roleVo, Principal principal) throws Exception{
            Integer roleCount = roleService.findRoleCount(roleVo);
            Integer roleId;
            if (roleCount.intValue()==0) {
                roleService.saveRole(roleVo);
                if (roleVo.getRoleId() != null) {
                    roleId = roleVo.getRoleId();
                } else {
                    roleId = roleService.findRoleIdByName(roleVo);
                }
            }else{
                roleId = roleService.findRoleIdByName(roleVo);
                modelMap.put("success","该角色组已有该角色!");
            }
            List<Map<String, Object>> tree = roleService.initRoleTree();
            modelMap.put("roleId", roleId);
            modelMap.put("tree", tree);
            /**功能权限控制**/
            permissionRender.renderRolePermission(modelMap, principal.getName());
            return "role/index";
    }

    @RequestMapping(value="/getRoleFunction",method = RequestMethod.GET)
    @ResponseBody
    public String getRoleFunction(@RequestParam Integer roleId) throws Exception{
        List<LinkedHashMap<String, Object>> functionVoList =  roleService.getRoleFunction(roleId);
        return (gson.toJson(functionVoList));
    }
}
