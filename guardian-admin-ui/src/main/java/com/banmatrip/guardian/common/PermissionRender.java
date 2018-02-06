package com.banmatrip.guardian.common;

import com.banmatrip.guardian.core.constants.DictionaryConstants;
import com.banmatrip.guardian.core.constants.PermissionUrlConstants;
import com.banmatrip.guardian.interfaces.permission.RolePermissionService;
import com.banmatrip.guardian.vo.permission.QueryFunctionPermissionVo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jepson
 * @Description: 权限渲染至页面
 * @create 2017-09-23 13:51
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Component
public class PermissionRender {


    @Autowired
    RolePermissionService rolePermissionService;


    /**
     * 角色权限渲染
     *
     * @param roleModel
     * @param  loginAccount
     */
    public void renderRolePermission(ModelMap roleModel,String loginAccount) {
        QueryFunctionPermissionVo queryParam = new QueryFunctionPermissionVo();
        queryParam.setLoginAccount(loginAccount);
        queryParam.setFunctionType(DictionaryConstants.FUNCTION_TYPE_ACCOUNT);
        Map<String,Object> roleMap =  rolePermissionService.getFunctionPermission(queryParam);
        List<String> list = new ArrayList<String>();
        /**添加角色组**/
        list.add(PermissionUrlConstants.ROLE_GROUP_ADD);
        /**编辑角色组**/
        list.add(PermissionUrlConstants.ROLE_GROUP_EDIT);
        /**删除角色组**/
        list.add(PermissionUrlConstants.ROLE_GROUP_DELETE);
        /**角色添加**/
        list.add(PermissionUrlConstants.ROLE_ADD);
        /**角色编辑**/
        list.add(PermissionUrlConstants.ROLE_EDIT);
        /**角色成员添加**/
        list.add(PermissionUrlConstants.ROLE_MEMBER_ADD);
        /**角色成员删除**/
        list.add(PermissionUrlConstants.ROLE_MEMBER_DELETE);
        /**导出成员角色信息**/
        list.add(PermissionUrlConstants.MEMBER_ROLE_DOWNLOAD);
        /**功能权限新增**/
        list.add(PermissionUrlConstants.FUNCTION_ADD);
        /**功能权限编辑**/
        list.add(PermissionUrlConstants.FUNCTION_EDIT);
        /**功能权限删除**/
        list.add(PermissionUrlConstants.FUNCTION_DELETE);
        /**字典表新增**/
        list.add(PermissionUrlConstants.DICTIONARY_ADD);
        /**字典表编辑**/
        list.add(PermissionUrlConstants.DICTIONARY_EDIT);
        /**字典表删除**/
        list.add(PermissionUrlConstants.DICTIONARY_DELETE);
        /**系统配置新增**/
        list.add(PermissionUrlConstants.SYSTEMCONFIG_ADD);
        /**系统配置编辑**/
        list.add(PermissionUrlConstants.SYSTEMCONFIG_EDIT);
        /**系统配置删除**/
        list.add(PermissionUrlConstants.SYSTEMCONFIG_DELETE);
        /**系统配置迁移**/
        list.add(PermissionUrlConstants.SYSTEMCONFIG_MIGRATE);
        /**权限设置**/
        list.add(PermissionUrlConstants.PERMISSION_SET);
        /**通用赋值处理**/
        commonRenderToPage(roleModel, (Map<String, Object>) roleMap.get(DictionaryConstants.FUNCTION_TYPE_ACCOUNT), list);
    }

    /**
     * 成员权限渲染
     *
     * @param memberMap
     * @param loginAccount
     */
    public void renderMemberPermission(ModelMap memberMap,String loginAccount) {
        QueryFunctionPermissionVo queryParam = new QueryFunctionPermissionVo();
        queryParam.setLoginAccount(loginAccount);
        queryParam.setFunctionType(DictionaryConstants.FUNCTION_TYPE_ACCOUNT);
        Map<String,Object> roleMap =  rolePermissionService.getFunctionPermission(queryParam);
        List<String> list = new ArrayList<String>();
        /**部门设置**/
        list.add(PermissionUrlConstants.DEPARTMENT_SET);
        /**添加下级部门**/
        list.add(PermissionUrlConstants.SUB_DEPARTMENT_ADD);
        /**部门员工添加**/
        list.add(PermissionUrlConstants.DEPARTMENT_EMPLOYEE_ADD);
        /**员工批量导入**/
        list.add(PermissionUrlConstants.EMPLOYEE_BATCH_IMPORT);
        /**员工编辑**/
        list.add(PermissionUrlConstants.EMPLOYEE_EDIT);
        /**功能权限批量管理**/
        list.add(PermissionUrlConstants.BATCH_MANAGEMENT);
        /**密码重置**/
        list.add(PermissionUrlConstants.PASSWORD_RESET);
        /**通用赋值处理**/
        commonRenderToPage(memberMap, (Map<String, Object>) roleMap.get(DictionaryConstants.FUNCTION_TYPE_ACCOUNT), list);
    }

    /**
     * 通用处理
     * @param memberMap
     * @param roleMap
     * @param list
     */
    private void commonRenderToPage(ModelMap memberMap,Map<String, Object> roleMap, List<String> list) {
        if (MapUtils.isNotEmpty(roleMap)) {
            for (Map.Entry<String,Object> entry : roleMap.entrySet()) {
                for (String url : list) {
                    if (url.equals(entry.getKey())) {
                        /**页面赋权**/
                        memberMap.addAttribute(entry.getKey().replaceAll("/",""),true);
                    }
                }
            }
        }
    }
}
