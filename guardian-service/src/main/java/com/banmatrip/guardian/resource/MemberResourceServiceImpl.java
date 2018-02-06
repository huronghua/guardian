package com.banmatrip.guardian.resource;

import com.banmatrip.guardian.core.utils.StringUtil;
import com.banmatrip.guardian.dto.request.resource.QueryDepartmentParam;
import com.banmatrip.guardian.dto.request.resource.QueryMemberParam;
import com.banmatrip.guardian.dto.response.resource.*;
import com.banmatrip.guardian.interfaces.resource.MemberResourceService;
import com.banmatrip.guardian.repository.mapper.resource.MemberArchitectureMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jepson
 * @Description:  成员架构资源服务
 * @create 2017-10-25 13:10
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Service
public class MemberResourceServiceImpl implements MemberResourceService {

    /**日志句柄**/
    private static final Logger log = LoggerFactory.getLogger(MemberResourceServiceImpl.class);
    /**根目录部门编号**/
    private static final String ROOT_PARENT_DEPARTMENT = "0";

    @Autowired
    MemberArchitectureMapper memberArchitectureMapper;

    /**
     * 查询成员信息
     *
     * @param queryMemberParam
     * @return
     */
    @Override
    public MemberInfo getMemberInfo(QueryMemberParam queryMemberParam) {
        return memberArchitectureMapper.getMemberInfo(queryMemberParam);
    }

    /**
     * 查询部门信息
     *
     * @param queryDepartmentParam
     * @return
     */
    @Override
    public List<DepartmentInfo> getDepartmentInfo(QueryDepartmentParam queryDepartmentParam) {
        return memberArchitectureMapper.getDepartmentInfo(queryDepartmentParam);
    }

    /**
     * 根据用户账号信息查询成员架构信息
     *
     * @param account
     * @return
     */
    @Override
    public MemberArchitectureInfo getMemberArchitectureInfoByAccount(String account) {
        QueryMemberParam queryMemberParam = new QueryMemberParam();
        QueryDepartmentParam queryDepartmentParam = new QueryDepartmentParam();
        queryMemberParam.setAccount(account);
        MemberInfo memberInfo =  this.getMemberInfo(queryMemberParam);
        List<DepartmentInfo> departmentInfoList = this.getDepartmentInfo(queryDepartmentParam);
        return assembleMemberArchitectureInfo(memberInfo,departmentInfoList);
    }


    /**
     * 根据用户账号信息查询所在部门成员信息
     *
     * @param account
     * @return
     */
    @Override
    public List<Map<Integer,List<UserInfo>>> getDepartmentMemberInfoByAccount(String account){
        /*获取账号所在所有部门*/
        List<Integer> departmentIdList=memberArchitectureMapper.getDepartmentIdListByAccount(account);
        /*根据所在部门获取部门成员*/
        List<Map<Integer,List<UserInfo>>> DepartmentMemberList=new ArrayList<Map<Integer,List<UserInfo>>>();
        if (CollectionUtils.isNotEmpty(departmentIdList)) {
            for (Integer departmentId : departmentIdList
                    ) {
                Map<Integer, List<UserInfo>> DepartmentMember = new HashMap<Integer, List<UserInfo>>();
                List<UserInfo> userList = memberArchitectureMapper.getUserListByDepartmentId(departmentId);
                DepartmentMember.put(departmentId, userList);
                DepartmentMemberList.add(DepartmentMember);
            }
        }
       return DepartmentMemberList;
    }

    @Override
    public AllUserDetailInfo getUserInfo(String ids) {
        AllUserDetailInfo userDetailList= new AllUserDetailInfo();
        List<UserDetailInfo> userDetailInfoList = new ArrayList();
        Map<Integer,UserDetailInfo> tmp = new HashMap();
        if(StringUtils.isNotEmpty(ids)){
            List<String> idList = StringUtil.addSingleQuoteToList(ids);
            userDetailInfoList = memberArchitectureMapper.getUserDetailsInfoByIds(idList);
        }else {
            userDetailInfoList = memberArchitectureMapper.getAllUserDetailsInfo();
        }
        for(UserDetailInfo detailInfo:userDetailInfoList){
            tmp.put(detailInfo.getId(),detailInfo);
        }
        userDetailList.setUserDetailInfo(tmp);
        return userDetailList;
    }

    /**
     * 组合成员架构信息
     *
     * @param memberInfo
     * @param departmentInfoList
     * @return
     */
    MemberArchitectureInfo assembleMemberArchitectureInfo(MemberInfo memberInfo, List<DepartmentInfo> departmentInfoList) {
        MemberArchitectureInfo memberArchitectureInfo = new MemberArchitectureInfo();
        /**设置成员架构信息**/
        memberArchitectureInfo.setMemberInfo(memberInfo);
        /**设置部门层级架构信息**/
        memberArchitectureInfo.setDepartmentStructs(parseDepartmentInfo(memberInfo,departmentInfoList));
        return memberArchitectureInfo;
    }

    /**
     * 解析部门信息
     *
     * @param  memberInfo
     * @param departmentInfoList
     * @return
     */
    List<DepartmentStruct> parseDepartmentInfo(MemberInfo memberInfo,List<DepartmentInfo> departmentInfoList) {
        List<DepartmentStruct> departmentStructList = new ArrayList<DepartmentStruct>();
        /**保存父级部门容器**/
        Map<String, DepartmentInfo> parentDepartmentMap = new HashMap<String, DepartmentInfo>();
        /**部门信息**/
        Map<String, DepartmentInfo> departmentInfoMap = new HashMap<String,DepartmentInfo>();
        /**初始化加载部门信息**/
        DepartmentInfo initLoadDepartment = new DepartmentInfo();
        if (CollectionUtils.isNotEmpty(departmentInfoList)) {
            for (DepartmentInfo departmentInfo : departmentInfoList) {
                /**根据父级部门编号设置部门信息**/
                if (!parentDepartmentMap.containsKey(departmentInfo.getParentDepartmentId())) {
                    parentDepartmentMap.put(departmentInfo.getParentDepartmentId(), departmentInfo);
                }
                /**根据父级部门编号设置部门信息**/
                if (!departmentInfoMap.containsKey(departmentInfo.getDepartmentId())) {
                    departmentInfoMap.put(departmentInfo.getDepartmentId(), departmentInfo);
                }
                /**获取匹配到的部门信息**/
                if (memberInfo.getDepartmentGroupId()!= null &&
                          memberInfo.getDepartmentGroupId().equals(departmentInfo.getDepartmentGroupId())) {
                    initLoadDepartment = departmentInfo;
                }
            }
        }
        /**级别最高部门，比如三级部门**/
        DepartmentStruct tailDepartment = new DepartmentStruct();
        tailDepartment.setChargName(initLoadDepartment.getChargeName());
        tailDepartment.setDepartmentName(initLoadDepartment.getDepartmentName());
        tailDepartment.setDepartmentLevel(initLoadDepartment.getDepartmentLevelName());
        /**最高级别部门入容器**/
        departmentStructList.add(tailDepartment);
        String parentDepartmentId = initLoadDepartment.getParentDepartmentId();
        if (StringUtils.isNotBlank(parentDepartmentId)) {
            /**开始循环--类递归逻辑**/
            while (true) {
                if (ROOT_PARENT_DEPARTMENT.equals(parentDepartmentId)) {
                        /**满足父节点为0条件，则跳出循环**/
                        break;
                } else {
                    DepartmentStruct departmentStruct = new DepartmentStruct();
                    DepartmentInfo loopDepartment =  departmentInfoMap.get(parentDepartmentId);
                    departmentStruct.setChargName(loopDepartment.getChargeName());
                    departmentStruct.setDepartmentName(loopDepartment.getDepartmentName());
                    departmentStruct.setDepartmentLevel(loopDepartment.getDepartmentLevelName());
                    departmentStructList.add(departmentStruct);
                    /**变更父级部门编号**/
                    parentDepartmentId = loopDepartment.getParentDepartmentId();
                }
            }
        }
        return departmentStructList;
    }
}