package com.banmatrip.guardian.permission;

import com.banmatrip.guardian.core.constants.DataRangeType;
import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.User;
import com.banmatrip.guardian.dto.response.permission.DataRangeListResult;
import com.banmatrip.guardian.dto.response.permission.DataRangePermisssion;
import com.banmatrip.guardian.dto.response.permission.DataRangeTypeAndId;
import com.banmatrip.guardian.dto.response.permission.FunctionPermission;
import com.banmatrip.guardian.interfaces.DepartmentService;
import com.banmatrip.guardian.interfaces.permission.RolePermissionService;
import com.banmatrip.guardian.interfaces.role.RoleService;
import com.banmatrip.guardian.repository.mapper.membership.UserMapper;
import com.banmatrip.guardian.repository.mapper.rolepermission.PermissionMapper;
import com.banmatrip.guardian.vo.permission.QueryFunctionPermissionVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author jepson
 * @Description:  角色权限服务
 * @create 2017-09-20 19:32
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    /**日志句柄**/
    private static final Logger log = LoggerFactory.getLogger(RolePermissionServiceImpl.class);
    private static final String DATA_RANGE_CHARGE = "allPermissions";
    private static final String ADMIN_TYPE = "0";
    private static final String DATA_RANGE_DESTINATION_TYPE = "destinationType";
    private static final String DATA_RANGE_PLATFORM_TYPE = "platformType";
    private static final String DATA_RANGE_PRODUCT_TYPE = "productType";
    private static final String DATA_RANGE_RESOURCE_TYPE = "resourceType";

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DepartmentService departmentService;

    /**
     * 功能权限
     *
     * @param queryParam
     * @return
     */
    @Override
    public Map<String,Object> getFunctionPermission(QueryFunctionPermissionVo queryParam) {
        /**功能列表**/
        List<FunctionPermission> functionList = permissionMapper.selectFunctionPermissionByLoginAccount(queryParam);
        Map<String,Object> typeMap = new HashMap<String, Object>();
        if (CollectionUtils.isNotEmpty(functionList)) {
            for (FunctionPermission functionPermission : functionList) {
                if (typeMap.containsKey(functionPermission.getFunctionType())) {
                    /**先取出在赋值**/
                    Map<String, Object> functionMap = (Map<String, Object>) typeMap.get(functionPermission.getFunctionType());
                    functionMap.put(functionPermission.getUrl(), true);
                } else {
                    Map<String, Object> functionMap = new HashMap<String, Object>();
                    /**功能权限置入Map中**/
                    functionMap.put(functionPermission.getUrl(), true);
                    typeMap.put(functionPermission.getFunctionType(), functionMap);
                }
            }
        }
        return typeMap;
    }

    /**
     * orange功能权限
     * @param queryParam
     * @return
     */
    @Override
    public Map<String, Object> getOrangeFunctionPermission(QueryFunctionPermissionVo queryParam) {
        /**功能列表**/
        Map<String,Object> finalResultMap = new HashMap<>();
        List<FunctionPermission> functionList = permissionMapper.selectOrangeFunctionPermissionByLoginAccount(queryParam);
        if (CollectionUtils.isNotEmpty(functionList)) {
            List<Map<String,Object>> functionMapList = new ArrayList<>();
            for (FunctionPermission functionPermission : functionList) {
                Map<String,Object> functionMap = new HashMap<String, Object>();
                functionMap.put("name",functionPermission.getName());
                functionMap.put("url",functionPermission.getUrl());
                functionMapList.add(functionMap);
            }
            finalResultMap.put("power",functionMapList);
        }
        List<Map<String,Object>> roleGroupIdList = permissionMapper.selectOrangeRoleGroupByLoginAccount(queryParam);
        if(CollectionUtils.isNotEmpty(roleGroupIdList)){
            finalResultMap.put("group",roleGroupIdList);
        }
        return finalResultMap;
    }

    /**
     * 数据范围权限
     *
     * @param loginAccount
     * @return
     */
    @Override
    public Map<String, Object> getDataPermission(String loginAccount) {
        Map<String,Object> reslutMap = new HashMap<String,Object>();
        /**1.获取角色类型是否有含admin**/
        DataRangeListResult listResult = this.selectDataRangeTypeByLoginAccount(loginAccount);
        /**2.获取出admin之外数据类型范围**/
        this.selectDataRangePermissionByLoginAccount(listResult,loginAccount);
        this.checkIsAll(listResult,loginAccount);
        /**3.返回结果赋值**/
        reslutMap.put(DATA_RANGE_DESTINATION_TYPE,listResult.getDestinationList());
        reslutMap.put(DATA_RANGE_PLATFORM_TYPE,listResult.getPlatformList());
        reslutMap.put(DATA_RANGE_PRODUCT_TYPE,listResult.getProductList());
        reslutMap.put(DATA_RANGE_RESOURCE_TYPE,listResult.getResourceList());
        return reslutMap;
    }

    /**
     *  根据登录账号获取数据权限
     *
     * @param loginAccount
     * @return
     */
    private DataRangeListResult  selectDataRangeTypeByLoginAccount(String loginAccount) {
        List<DataRangePermisssion> dataRangeList = permissionMapper.selectDataRangeTypeByLoginAccount(loginAccount);
        DataRangeListResult listResult = new DataRangeListResult();
        if (CollectionUtils.isNotEmpty(dataRangeList)) {
            for (DataRangePermisssion dataRangePermisssion : dataRangeList) {
                if (ADMIN_TYPE.equals(dataRangePermisssion.getDestinationType())
                        && !listResult.getDestinationList().contains(DATA_RANGE_CHARGE)) {
                    listResult.getDestinationList().add(DATA_RANGE_CHARGE);
                }
                if (ADMIN_TYPE.equals(dataRangePermisssion.getPlatformType())
                        && !listResult.getPlatformList().contains(DATA_RANGE_CHARGE)) {
                    listResult.getPlatformList().add(DATA_RANGE_CHARGE);
                }
                if (ADMIN_TYPE.equals(dataRangePermisssion.getProductType())
                        && !listResult.getProductList().contains(DATA_RANGE_CHARGE)) {
                    listResult.getProductList().add(DATA_RANGE_CHARGE);
                }
                if (ADMIN_TYPE.equals(dataRangePermisssion.getResourceType())
                        && !listResult.getResourceList().contains(DATA_RANGE_CHARGE)) {
                    listResult.getResourceList().add(DATA_RANGE_CHARGE);
                }
            }
        }
        return listResult;
    }

    /**
     * 根据登录账号获取数据权限
     *
     * @param loginAccount
     * @param listResult
     */
/*   private void selectDataRangePermissionByLoginAccount(DataRangeListResult listResult ,String loginAccount) {
       List<DataRangeTypeAndId> dataRangeTypeAndIdList =
               permissionMapper.selectDataRangePermissionByLoginAccount(loginAccount);
       if (CollectionUtils.isNotEmpty(dataRangeTypeAndIdList)) {
           for (DataRangeTypeAndId dataRangeTypeAndId : dataRangeTypeAndIdList) {
               if (!listResult.getDestinationList().contains(DATA_RANGE_CHARGE) &&
                       DataRangeType.DESTINATION_TYPE.equals(dataRangeTypeAndId.getDataRangeType())) {
                   listResult.getDestinationList().add(dataRangeTypeAndId.getDataRangeId());
               } else if (!listResult.getPlatformList().contains(DATA_RANGE_CHARGE) &&
                       DataRangeType.PLATFORM_TYPE.equals(dataRangeTypeAndId.getDataRangeType())) {
                   listResult.getPlatformList().add(dataRangeTypeAndId.getDataRangeId());
               } else if (!listResult.getProductList().contains(DATA_RANGE_CHARGE) &&
                       DataRangeType.PRODUCT_TYPE.equals(dataRangeTypeAndId.getDataRangeType())) {
                   listResult.getProductList().add(dataRangeTypeAndId.getDataRangeId());
               } else if (!listResult.getResourceList().contains(DATA_RANGE_CHARGE) &&
                       DataRangeType.RESOURCE_TYPE.equals(dataRangeTypeAndId.getDataRangeType())) {
                   listResult.getResourceList().add(dataRangeTypeAndId.getDataRangeId());
               } else {
                   log.info("无效数据范围类型!");
                   return ;
               }
           }
       }
   }*/

    /**
     *
     * @param listResult
     * @param loginAccount
     */
    private void selectDataRangePermissionByLoginAccount(DataRangeListResult listResult, String loginAccount) {
        /*获取用户所有数据范围*/
        /*本人相关时获取数据范围数据*/
        List<DataRangeTypeAndId> dataRangeTypeAndIdListByUsr = permissionMapper.selectDataRangePermissionByLoginAccountAndUsr(loginAccount);
        /*本部门相关时获取数据范围数据*/
        List<Integer> departmentIdList = userMapper.findDepartmentIdByAccount(loginAccount);
        Map<String,List<Integer>> depParams=new HashMap<String,List<Integer>>();
        depParams.put("departmentIdList",departmentIdList);
        List<DataRangeTypeAndId> dataRangeTypeAndIdListByDep = permissionMapper.selectDataRangePermissionByLoginAccountAndDep(depParams);
       /*本部门及下属部门相关时获取数据范围数据*/
        List<Integer> subDepartmentIdList = findSubDepartment(loginAccount);
        Map<String,List<Integer>> subDepParams=new HashMap<String,List<Integer>>();
        subDepParams.put("departmentIdList",subDepartmentIdList);
        List<DataRangeTypeAndId> dataRangeTypeAndIdListBySubDep = permissionMapper.selectDataRangePermissionByLoginAccountAndDep(subDepParams);
        /*根据数据范围类型赋予用户数据范围权限*/
        List<DataRangePermisssion> dataRangeList = permissionMapper.selectDataRangeTypeByLoginAccount(loginAccount);
        Map<String,List<DataRangeTypeAndId>> paramMap = new HashMap<String,List<DataRangeTypeAndId>>();
        paramMap.put("1",dataRangeTypeAndIdListByUsr);
        paramMap.put("2",dataRangeTypeAndIdListByDep);
        paramMap.put("3",dataRangeTypeAndIdListBySubDep);
        if (CollectionUtils.isNotEmpty(dataRangeList)) {
            for (DataRangePermisssion dataRangePermisssion : dataRangeList) {
                /**目的地**/
                this.assembleDataRangePermission(dataRangePermisssion,listResult,paramMap,dataRangePermisssion.getDestinationType(),"1");
                /**渠道**/
                this.assembleDataRangePermission(dataRangePermisssion,listResult,paramMap,dataRangePermisssion.getPlatformType(),"2");
                /**品类**/
                this.assembleDataRangePermission(dataRangePermisssion,listResult,paramMap,dataRangePermisssion.getProductType(),"3");
                /**资源**/
                this.assembleDataRangePermission(dataRangePermisssion,listResult,paramMap,dataRangePermisssion.getResourceType(),"4");
            }
        }
    }


    /**
         * 根据账号查询用户信息
         *
         * @param account
         * @return
         */
        @Override
        public Map<String, Object> getUserByAccount(String account) {
            User user = userMapper.selectByAccount(account);
            Map<String,Object> userInfo = new HashMap<String,Object>();
            userInfo.put("id",user.getId());
            userInfo.put("name",user.getName());
            userInfo.put("departmentId",user.getDepartmentId());
            userInfo.put("employeeId",user.getEmployeeId());
            userInfo.put("roleType",user.getRoleType());
            userInfo.put("account",user.getAccount());
            userInfo.put("cellphone",user.getCellphone());
            userInfo.put("email",user.getEmail());
            userInfo.put("orangeAccount",user.getAccount());
            return userInfo;
    }


    /**
     * 查询部门及其下属部门
     *
     * @param loginAccount
     * @return
     */
    public List<Integer> findSubDepartment(String loginAccount){
        Set<Integer> set=new HashSet();
        List<Integer> departmentIdList = userMapper.findDepartmentIdByAccount(loginAccount);
        for (Integer departmentId:
                departmentIdList) {
            List<Department> departmentList=new ArrayList();
            /*List<Department> departmentList=departmentService.findChildDepartment(departmentId,tree);*/
            departmentService.findChildDepartment(departmentId,departmentList);
            set.add(departmentId);
            for (Department department:departmentList
                    ) {
                set.add(department.getId());
            }
        }
        List<Integer> result = new ArrayList<>(set);
        return result;
    }


    /**
     * 拼装数据权限
     *
     * @param dataRangePermisssion
     * @param listResult
     * @param paramMap
     * @param orgType
     * @param dataRangeType
     */
    private void assembleDataRangePermission(DataRangePermisssion dataRangePermisssion, DataRangeListResult listResult,
                                             Map<String, List<DataRangeTypeAndId>> paramMap, String orgType, String dataRangeType) {
        if (DataRangeType.DESTINATION_TYPE.equals(dataRangeType)) {
                List<DataRangeTypeAndId> dataRangeList = paramMap.get(orgType);
                if (CollectionUtils.isNotEmpty(dataRangeList)) {
                    for (DataRangeTypeAndId dataRangeTypeAndId : dataRangeList) {
                        if (!listResult.getDestinationList().contains(DATA_RANGE_CHARGE) &&
                                dataRangeType.equals(dataRangeTypeAndId.getDataRangeType())) {
                            listResult.getDestinationList().add(dataRangeTypeAndId.getDataRangeId());
                        }
                    }
                }
        } else if (DataRangeType.PLATFORM_TYPE.equals(dataRangeType)) {
                List<DataRangeTypeAndId> dataRangeList = paramMap.get(orgType);
                if (CollectionUtils.isNotEmpty(dataRangeList)) {
                    for (DataRangeTypeAndId dataRangeTypeAndId : dataRangeList) {
                        if (!listResult.getPlatformList().contains(DATA_RANGE_CHARGE) &&
                                dataRangeType.equals(dataRangeTypeAndId.getDataRangeType())) {
                            listResult.getPlatformList().add(dataRangeTypeAndId.getDataRangeId());
                        }
                    }
            }
        } else if (DataRangeType.PRODUCT_TYPE.equals(dataRangeType)) {
                List<DataRangeTypeAndId> dataRangeList = paramMap.get(orgType);
                if (CollectionUtils.isNotEmpty(dataRangeList)) {
                    for (DataRangeTypeAndId dataRangeTypeAndId : dataRangeList) {
                        if (!listResult.getProductList().contains(DATA_RANGE_CHARGE) &&
                                dataRangeType.equals(dataRangeTypeAndId.getDataRangeType())) {
                            listResult.getProductList().add(dataRangeTypeAndId.getDataRangeId());
                        }
                    }
            }
        } else if (DataRangeType.RESOURCE_TYPE.equals(dataRangeType)) {
                List<DataRangeTypeAndId> dataRangeList = paramMap.get(orgType);
                if (CollectionUtils.isNotEmpty(dataRangeList)) {
                    for (DataRangeTypeAndId dataRangeTypeAndId : dataRangeList) {
                        if (!listResult.getResourceList().contains(DATA_RANGE_CHARGE) &&
                                dataRangeType.equals(dataRangeTypeAndId.getDataRangeType())) {
                            listResult.getResourceList().add(dataRangeTypeAndId.getDataRangeId());
                        }
                    }
            }
        } else {
            log.error("错误数据类型!");
        }
    }
    private void checkIsAll(DataRangeListResult listResult, String loginAccount){
        List<String> desIdList = new ArrayList<>();
        List<String> proIdList = new ArrayList<>();
        List<String> resIdList = new ArrayList<>();
        List<String> plaIdList = new ArrayList<>();
        /*获取用户所有数据范围*/
        /**
         * 本人相关时获取数据范围数据
         * */
        List<DataRangeTypeAndId> dataRangeTypeAndIdListByUsr = permissionMapper.selectDataRangePermissionByLoginAccountAndUsr(loginAccount);
        /**
         * 本部门相关时获取数据范围数据
         * */
        List<Integer> departmentIdList = userMapper.findDepartmentIdByAccount(loginAccount);
        Map<String,List<Integer>> depParams=new HashMap<String,List<Integer>>();
        depParams.put("departmentIdList",departmentIdList);
        List<DataRangeTypeAndId> dataRangeTypeAndIdListByDep = permissionMapper.selectDataRangePermissionByLoginAccountAndDep(depParams);
        /**
         * 本部门及下属部门相关时获取数据范围数据
         * */
        List<Integer> subDepartmentIdList = findSubDepartment(loginAccount);
        Map<String,List<Integer>> subDepParams=new HashMap<String,List<Integer>>();
        subDepParams.put("departmentIdList",subDepartmentIdList);
        List<DataRangeTypeAndId> dataRangeTypeAndIdListBySubDep = permissionMapper.selectDataRangePermissionByLoginAccountAndDep(subDepParams);
        List<DataRangePermisssion> dataRangeList = permissionMapper.selectDataRangeTypeByLoginAccount(loginAccount);
        for (DataRangePermisssion dataRangePermisssion:dataRangeList){
            if ("1".equals(dataRangePermisssion.getDestinationType())){
                this.addDataRangeIdList(desIdList,null,null,null,dataRangeTypeAndIdListByUsr);
            }if ("2".equals(dataRangePermisssion.getDestinationType())){
                this.addDataRangeIdList(desIdList,null,null,null,dataRangeTypeAndIdListByDep);
            }if ("3".equals(dataRangePermisssion.getDestinationType())){
                this.addDataRangeIdList(desIdList,null,null,null,dataRangeTypeAndIdListBySubDep);
            }
            if ("1".equals(dataRangePermisssion.getPlatformType())){
                this.addDataRangeIdList(null,null,null,plaIdList,dataRangeTypeAndIdListByUsr);
            }if ("2".equals(dataRangePermisssion.getPlatformType())){
                this.addDataRangeIdList(null,null,null,plaIdList,dataRangeTypeAndIdListByDep);
            }if ("3".equals(dataRangePermisssion.getPlatformType())){
                this.addDataRangeIdList(null,null,null,plaIdList,dataRangeTypeAndIdListBySubDep);
            }
            if ("1".equals(dataRangePermisssion.getProductType())){
                this.addDataRangeIdList(null,proIdList,null,null,dataRangeTypeAndIdListByUsr);
            }if ("2".equals(dataRangePermisssion.getProductType())){
                this.addDataRangeIdList(null,proIdList,null,null,dataRangeTypeAndIdListByDep);
            }if ("3".equals(dataRangePermisssion.getProductType())){
                this.addDataRangeIdList(null,proIdList,null,null,dataRangeTypeAndIdListBySubDep);
            }
            if ("1".equals(dataRangePermisssion.getResourceType())){
                this.addDataRangeIdList(null,null,resIdList,null,dataRangeTypeAndIdListByUsr);
            }if ("2".equals(dataRangePermisssion.getResourceType())){
                this.addDataRangeIdList(null,null,resIdList,null,dataRangeTypeAndIdListByDep);
            }if ("3".equals(dataRangePermisssion.getResourceType())){
                this.addDataRangeIdList(null,null,resIdList,null,dataRangeTypeAndIdListBySubDep);
            }
        }
        /**
         * 判断是否包含全部选项
         * **/
        if (desIdList!=null&&desIdList.contains("-999")){
            listResult.getDestinationList().clear();
            listResult.getDestinationList().add(DATA_RANGE_CHARGE);
        }
        if (proIdList!=null&&proIdList.contains("-999")){
            listResult.getProductList().clear();
            listResult.getProductList().add(DATA_RANGE_CHARGE);
        }
        if (resIdList!=null&&resIdList.contains("-999")){
            listResult.getResourceList().clear();
            listResult.getResourceList().add(DATA_RANGE_CHARGE);
        }
        if (plaIdList!=null&&plaIdList.contains("-999")){
            listResult.getPlatformList().clear();
            listResult.getPlatformList().add(DATA_RANGE_CHARGE);
        }

    }

    private void addDataRangeIdList(List<String> desIdList,List<String> proIdList,List<String> resIdList, List<String> plaIdList,List<DataRangeTypeAndId> dataRangeTypeAndIds){
        if (null != dataRangeTypeAndIds) {
            for (DataRangeTypeAndId dataRangeTypeAndId : dataRangeTypeAndIds) {
                if (desIdList!=null&dataRangeTypeAndId.getDataRangeType().equals(DataRangeType.DESTINATION_TYPE)){
                    desIdList.add(dataRangeTypeAndId.getDataRangeId());
                }
                if (proIdList!=null&dataRangeTypeAndId.getDataRangeType().equals(DataRangeType.PRODUCT_TYPE)){
                    proIdList.add(dataRangeTypeAndId.getDataRangeId());
                }
                if (resIdList!=null&&dataRangeTypeAndId.getDataRangeType().equals(DataRangeType.RESOURCE_TYPE)){
                    resIdList.add(dataRangeTypeAndId.getDataRangeId());
                }
                if (plaIdList!=null&&dataRangeTypeAndId.getDataRangeType().equals(DataRangeType.PLATFORM_TYPE)){
                    plaIdList.add(dataRangeTypeAndId.getDataRangeId());
                }
            }
        }
    }

}