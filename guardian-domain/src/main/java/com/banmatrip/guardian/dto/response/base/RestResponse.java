package com.banmatrip.guardian.dto.response.base;

/**
 * @author jepson
 * @Description:
 * @create 2017-09-19 10:01
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/

import com.banmatrip.guardian.dto.response.resource.AllUserDetailInfo;
import com.banmatrip.guardian.dto.response.resource.UserInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用说明：显示参数
 *
 */
public class RestResponse implements Serializable {

    private AllUserDetailInfo userDetailList;
    //成功/失败标志
    private boolean             success;
    //成功/失败描述信息
    protected String            message;
    /**
     * 结果代码
     * <ul>
     * <li>success==true 暂不需要code</li>
     * <li>success==false && code==9999 : 为系统异常，跳转到错误页面(如404)</li>
     * <li>success==false && code==其他值 : 为错误提示信息，用于在页面显示，如认证未通过</li>
     * </ul>
     */
    protected String            code;
    private Map<String, Object> data;
    private List<Map<Integer,List<UserInfo>>> departmentInfoData;


    public RestResponse() {
    }
    public RestResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public RestResponse(boolean success, String message, Map<String, Object> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    public RestResponse(boolean success, String message ,String code, Map<String, Object> data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    /*返回部门成员信息*/
    public RestResponse(boolean success, String message ,String code, List<Map<Integer,List<UserInfo>>> departmentInfoData) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.departmentInfoData = departmentInfoData;
    }

    public RestResponse(boolean success,String message ,String code, AllUserDetailInfo userDetailList) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.userDetailList = userDetailList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Map<Integer, List<UserInfo>>> getDepartmentInfoData() {
        return departmentInfoData;
    }

    public void setDepartmentInfoData(List<Map<Integer, List<UserInfo>>> departmentInfoData) {
        this.departmentInfoData = departmentInfoData;
    }

    public AllUserDetailInfo getUserDetailList() {
        return userDetailList;
    }

    public void setUserDetailList(AllUserDetailInfo userDetailList) {
        this.userDetailList = userDetailList;
    }

    @Override
    public String toString() {
        return "RestResponse [success=" + success + ", message=" + message + ", code=" + code + ", data=" + data + "]";
    }
}