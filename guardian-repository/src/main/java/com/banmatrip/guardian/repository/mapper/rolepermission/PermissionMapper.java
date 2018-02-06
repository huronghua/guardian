package com.banmatrip.guardian.repository.mapper.rolepermission;

import com.banmatrip.guardian.dto.response.permission.DataRangePermisssion;
import com.banmatrip.guardian.dto.response.permission.DataRangeTypeAndId;
import com.banmatrip.guardian.dto.response.permission.FunctionPermission;
import com.banmatrip.guardian.vo.permission.QueryFunctionPermissionVo;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 *  create by jepson on 2017/09/23
 */
public interface PermissionMapper {

    /**
     * 获取功能权限
     *
     * @param queryParam
     * @return
     */
    List<FunctionPermission> selectFunctionPermissionByLoginAccount(QueryFunctionPermissionVo queryParam);

    /**
     * 根据登录账号获取数据范围权限
     *
     * @param loginAccount
     * @return
     */
    List<DataRangeTypeAndId> selectDataRangePermissionByLoginAccount(String loginAccount);


    /**
     * 根据登录账号获取数据范围类型
     *
     * @param loginAccount
     * @return
     */
    List<DataRangePermisssion> selectDataRangeTypeByLoginAccount(String loginAccount);

    /*
    *本人相关时根据登录账号获取数据范围权限
    *
    *@param  loginAccount
    *@return
    * */
    List<DataRangeTypeAndId> selectDataRangePermissionByLoginAccountAndUsr(String loginAccount);

    /*
    * 本部门相关时根据部门id列表获取数据范围权限
    * @param  departmentIdList
    * @return
    * */
    List<DataRangeTypeAndId> selectDataRangePermissionByLoginAccountAndDep(Map<String,List<Integer>> depParams);

    /**
     * 获取orange用户功能权限
     * @param queryParam
     * @return
     */
    List<FunctionPermission> selectOrangeFunctionPermissionByLoginAccount(QueryFunctionPermissionVo queryParam);


    /**
     * 获取orange用户角色组id
     * @param queryParam
     * @return
     */
    List<Map<String,Object>> selectOrangeRoleGroupByLoginAccount(QueryFunctionPermissionVo queryParam);
}
