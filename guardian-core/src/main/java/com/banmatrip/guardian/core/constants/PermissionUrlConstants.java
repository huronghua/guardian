package com.banmatrip.guardian.core.constants;

/**
 * @author jepson
 * @Description: 权限管理规则系统设置
 * @create 2017-09-23 15:09
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class PermissionUrlConstants {

    /**角色组添加**/
    public static final String ROLE_GROUP_ADD = "account/role/group/add";
    /**角色组编辑**/
    public static final String ROLE_GROUP_EDIT = "account/role/group/edit";
    /**角色组删除**/
    public static final String ROLE_GROUP_DELETE = "account/role/group/delete";

    /**角色添加**/
    public static final String ROLE_ADD = "account/role/add";
    /**修改角色**/
    public static final String ROLE_EDIT = "account/role/edit";
    /**添加角色成员**/
    public static final String ROLE_MEMBER_ADD = "account/role/member/add";
    /**删除角色成员**/
    public static final String ROLE_MEMBER_DELETE = "account/role/member/delete";
    /**角色成员信息导出**/
    public static final String MEMBER_ROLE_DOWNLOAD = "account/role/member/export";

    /**权限设置**/
    public static final String PERMISSION_SET = "account/permission/set";

    /**功能权限新增**/
    public static final String FUNCTION_ADD = "account/function/add";
    /**功能权限编辑**/
    public static final String FUNCTION_EDIT = "account/function/edit";
    /**功能权限删除**/
    public static final String FUNCTION_DELETE = "account/function/delete";


    /**字典表新增**/
    public static final String DICTIONARY_ADD = "dictionary/add";
    /**字典表编辑**/
    public static final String DICTIONARY_EDIT = "dictionary/edit";
    /**字典表删除**/
    public static final String DICTIONARY_DELETE = "dictionary/delete";

    /**系统配置新增**/
    public static final String SYSTEMCONFIG_ADD = "system/config/add";
    /**系统配置编辑**/
    public static final String SYSTEMCONFIG_EDIT = "system/config/edit";
    /**系统配置删除**/
    public static final String SYSTEMCONFIG_DELETE = "system/config/delete";
    /**系统配置迁移**/
    public static final String SYSTEMCONFIG_MIGRATE = "system/config/migrate";

    /**部门设置**/
    public static final String DEPARTMENT_SET = "account/department/set";
    /**添加下级部门**/
    public static final String SUB_DEPARTMENT_ADD = "account/subdepartment/add";
    /**添加部门员工**/
    public static final String DEPARTMENT_EMPLOYEE_ADD = "account/department/employee/add";
    /**员工批量导入**/
    public static final String EMPLOYEE_BATCH_IMPORT = "account/employee/batch/import";
    /**员工编辑**/
    public static final String EMPLOYEE_EDIT = "account/employee/edit";
    /**功能权限批量管理**/
    public static final String BATCH_MANAGEMENT = "account/function/batch/management";
    /**密码重置**/
    public static final String PASSWORD_RESET = "account/function/password/reset";
}