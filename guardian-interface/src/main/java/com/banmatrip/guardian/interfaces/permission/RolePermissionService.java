package com.banmatrip.guardian.interfaces.permission;

import com.banmatrip.guardian.vo.permission.QueryFunctionPermissionVo;

import java.util.Map;

/**
 *  角色权限
 *  create by jepson on 2017/09/20
 */
public interface RolePermissionService {


    /**
     * 功能权限
     *
     * @param queryParam
     * @return
     */
    public Map<String,Object> getFunctionPermission(QueryFunctionPermissionVo queryParam);


    /**
     * 数据范围权限
     *
     * @param loginAccount
     * @return
     */
    public Map<String,Object> getDataPermission(String loginAccount);


    /**
     * 根据账号查询用户信息
     *
     * @param account
     * @return
     */
    public Map<String,Object> getUserByAccount(String account);


    /**
     * 根据用户名查询Orange权限
     * @param account
     * @return
     */
    public Map<String,Object> getOrangeFunctionPermission(QueryFunctionPermissionVo queryParam);

}
