package com.banmatrip.guardian.assemble;

import com.banmatrip.guardian.core.utils.Md5Utils;
import com.banmatrip.guardian.core.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Miracle Xu
 * @Description: user数据组装from excel
 * @create 2017-09-20 13:35
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class UserAssemble {
    //excel数据组装转存
    public static List<Map<String,Object>> userAssemble(List<Map<String,Object>> list){
        List<Map<String,Object>> userList = new ArrayList();
        for(int i = 0;i<list.size();i++){
            Map<String,Object> tmp = list.get(i);
            Map<String,Object> oneUser = new HashMap();//一个user所有的信息

            Map<String,Object> user = new HashMap();//用户基础信息
            if(null == tmp.get("name") || StringUtils.isBlank(tmp.get("name").toString())){
                continue;
            }
            user.put("name",tmp.get("name"));//姓名
            user.put("account",tmp.get("account"));//账号
            user.put("password",tmp.get("password"));//密码
            user.put("roleType",tmp.get("roleType"));//角色类别
            user.put("position",tmp.get("position"));//岗位
            user.put("userCellphone",tmp.get("cellphone"));//手机号
            user.put("email",tmp.get("email"));//企业邮箱
            user.put("employeeId",tmp.get("employeeId"));//工号
            user.put("ethnic",tmp.get("ethnic"));//族群
            user.put("role",tmp.get("role"));//角色

            List<String> department = StringUtil.addSingleQuoteToList((String) tmp.get("department"));//部门list
            String department_1 = (String) tmp.get("department_1");//一级部门
            String department_2 = (String) tmp.get("department_2");//二级部门
            String department_3 = (String) tmp.get("department_3");//三级部门
            List<String> destination = StringUtil.addSingleQuoteToList((String) tmp.get("tag"));//目的地list
            List<String> platform = StringUtil.addSingleQuoteToList((String) tmp.get("orderPlatform"));//渠道list
            List<String> productType = StringUtil.addSingleQuoteToList((String) tmp.get("productType"));//产品类型list
            List<String> resourceType = StringUtil.addSingleQuoteToList((String) tmp.get("resourceType"));//产品类型list

            oneUser.put("user",user);//保存基础信息
            oneUser.put("department",department);//保存部门信息
            oneUser.put("department_1",department_1);//保存一级部门信息
            oneUser.put("department_2",department_2);//保存二级部门信息
            oneUser.put("department_3",department_3);//保存三级部门信息
            oneUser.put("destination",destination);//目的地
            oneUser.put("platform",platform);//渠道
            oneUser.put("productType",productType);//产品类型
            oneUser.put("resourceType",resourceType);//资源类型

            userList.add(oneUser);//存入user list
        }
        return userList;
    }

    //user的目的地/渠道/产品类型/资源类型信息组装
    public static List<Map<String,Object>> dataListAssemble(List<Map<String,Object>> userList,List<List<Map>> list,String type,int rangeType){
        //批量存入用户的目的地/渠道/产品类型/资源类型信息
        List<Map<String,Object>> dataList = new ArrayList();
        if(CollectionUtils.isNotEmpty(userList) && CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < userList.size(); i++) {
                Map<String, Object> tmp = new HashMap();
                if (list.get(i).size() == 2) {
                    Map user = userList.get(i);//用户信息
                    Map<String,ArrayList> conf = list.get(i).get(1);//目的地/渠道/产品类型/资源类型信息
                    List<Object> data = conf.get(type);
                    if(CollectionUtils.isEmpty(data)) continue;//该人员的该资源为空则跳过
                    tmp.put("id", user.get("id"));
                    tmp.put("data",data);
                    tmp.put("rangeType",rangeType);
                    dataList.add(tmp);
                }
            }
        }
        return dataList;
    }

    //user基本信息组装
    public static List<Map<String,Object>> userBaseInfoListAssemble(List<List<Map>> list,int id){
        List<Map<String,Object>> userList = new ArrayList();//用户基本信息
        if(CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                if (CollectionUtils.isNotEmpty(list.get(i))) {
                    Map user = list.get(i).get(0);
                    user.put("createId",id);
                    user.put("updateId",id);
                    String passwordTmp = "123456";
                    String password = Md5Utils.encodeMD5Hex(passwordTmp);
                    user.put("password",password);//密码自动拼接,转Md5摘要存储
                    userList.add(user);//用户基本信息
                }
            }
        }
        return userList;
    }

    //批量用户信息修改数据组装
    public static List<Map<String,Object>> batchMemberEditListAssemble(HttpServletRequest request,Integer id){
        List<Map<String,Object>> batchMemberInfoList = new ArrayList();
        String memberIdString = request.getParameter("memberIdList");
        String mem = memberIdString == null ? "" : memberIdString.substring(1,memberIdString.length()-1);
        String departmentListString = request.getParameter("departmentList");
        String roleListString = request.getParameter("roleList");
        String tagString = request.getParameter("tag");
        String orderPlatformString = request.getParameter("orderPlatform");
        String productTypeString = request.getParameter("productType");
        String resourceTypeString = request.getParameter("resourceType");

        List<Integer> memberIdList = StringUtil.addSingleQuoteToList(mem);
        List<Integer> departmentList = StringUtil.addSingleQuoteToList(departmentListString);
        List<Integer> roleList = StringUtil.addSingleQuoteToList(roleListString) == null ? new ArrayList() : StringUtil.addSingleQuoteToList(roleListString);
        List<Integer> tagList = StringUtil.addSingleQuoteToList(tagString);
        List<Integer> orderPlatformList = StringUtil.addSingleQuoteToList(orderPlatformString);
        List<Integer> productTypeList = StringUtil.addSingleQuoteToList(productTypeString);
        List<Integer> resourceTypeList = StringUtil.addSingleQuoteToList(resourceTypeString);

        for(int i = 0;i<memberIdList.size();i++){
            Map<String,Object> tmp = new HashMap();
            tmp.put("updateId",id);
            tmp.put("id",memberIdList.get(i));
            tmp.put("department",departmentList);
            tmp.put("role",roleList);
            tmp.put("tag",tagList);
            tmp.put("orderPlatform",orderPlatformList);
            tmp.put("productType",productTypeList);
            tmp.put("resourceType",resourceTypeList);
            batchMemberInfoList.add(tmp);
        }
        return batchMemberInfoList;
    }

    //user的目的地/渠道/产品类型/资源类型信息组装
    public static List<Map<String,Object>> dataListBatchAssemble(List<Map<String,Object>> list,String type,int rangeType){
        //批量存入用户的目的地/渠道/产品类型/资源类型信息
        List<Map<String,Object>> dataList = new ArrayList();
        if(CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                Map info = list.get(i);
                Map<String, Object> tmp = new HashMap();
                List<Object> data = (List<Object>) info.get(type);
                if(CollectionUtils.isEmpty(data)){
                    continue;//该人员的该资源为空则跳过
                }
                tmp.put("id", info.get("id"));
                tmp.put("data",data);
                tmp.put("rangeType",rangeType);
                dataList.add(tmp);

            }
        }
        return dataList;
    }

    //user的角色和部门筛选
    public static List<Map<String,Object>> dataListBatchRoleAndDeptAssemble(List<Map<String,Object>> list,String type){
        //批量存入用户的目的地/渠道/产品类型/资源类型信息
        List<Map<String,Object>> dataList = new ArrayList();
        if(CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                Map info = list.get(i);
                List<Object> data = (List<Object>) info.get(type);
                if(CollectionUtils.isEmpty(data)){
                    continue;//该人员的该资源数据为空则跳过
                }else {
                    dataList.add(info);
                }
            }
        }
        return dataList;
    }
}
