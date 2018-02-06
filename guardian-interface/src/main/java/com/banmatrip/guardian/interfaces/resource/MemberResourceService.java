package com.banmatrip.guardian.interfaces.resource;

import com.banmatrip.guardian.dto.request.resource.QueryDepartmentParam;
import com.banmatrip.guardian.dto.request.resource.QueryMemberParam;
import com.banmatrip.guardian.dto.response.resource.*;

import java.util.List;
import java.util.Map;

/***
 *  成员资源服务
 */
public interface MemberResourceService {

    /**
     * 查询成员信息
     * @param queryMemberParam
     * @return
     */
    MemberInfo getMemberInfo(QueryMemberParam queryMemberParam);


    /**
     *  查询部门信息
     *
     * @param queryDepartmentParam
     * @return
     */
    List<DepartmentInfo> getDepartmentInfo(QueryDepartmentParam queryDepartmentParam);


    /**
     * 根据用户账号信息查询成员架构信息
     *
     * @param account
     * @return
     */
    MemberArchitectureInfo getMemberArchitectureInfoByAccount(String account);


    /**
     * 根据用户账号信息查询所在部门成员信息
     *
     * @param account
     * @return
     */
    List<Map<Integer,List<UserInfo>>> getDepartmentMemberInfoByAccount(String account);

    /**
     * 根据用户id查询用户信息
     *
     * @param
     * @return
     */
    AllUserDetailInfo getUserInfo(String ids);
}
