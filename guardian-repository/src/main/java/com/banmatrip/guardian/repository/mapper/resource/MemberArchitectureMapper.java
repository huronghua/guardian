package com.banmatrip.guardian.repository.mapper.resource;

import com.banmatrip.guardian.dto.request.resource.QueryDepartmentParam;
import com.banmatrip.guardian.dto.request.resource.QueryMemberParam;
import com.banmatrip.guardian.dto.response.resource.DepartmentInfo;
import com.banmatrip.guardian.dto.response.resource.MemberInfo;
import com.banmatrip.guardian.dto.response.resource.UserDetailInfo;
import com.banmatrip.guardian.dto.response.resource.UserInfo;

import java.util.List;
import java.util.Map;

public interface MemberArchitectureMapper {

    /**
     *  获取成员信息
     *
     * @param queryParam
     * @return
     */
    MemberInfo getMemberInfo(QueryMemberParam queryParam);

    /**
     *  获取部门信息
     *
     * @param queryDepartmentParam
     * @return
     */
    List<DepartmentInfo> getDepartmentInfo(QueryDepartmentParam queryDepartmentParam);


    /**
     * 根据账号获取所在部门id
     *
     * @param account
     * @return
     */
    List<Integer> getDepartmentIdListByAccount(String account);


    List<UserInfo> getUserListByDepartmentId(Integer departmentId);


    List<UserDetailInfo> getUserDetailsInfoByIds(List<String> idList);

    List<UserDetailInfo> getAllUserDetailsInfo();
}
