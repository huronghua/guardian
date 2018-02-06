package com.banmatrip.guardian.assemble;

import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.Role;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Miracle Xu
 * @Description: 部门数据组装
 * @create 2017-09-17 14:17
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class DepartmentAssemble {
    //部门数据范围信息
    public static List<Map<String,Object>> assembleDepartment(List<String> param, Department department, String dataRangeType){
        List<Map<String,Object>> list = new ArrayList();
        //部门数据范围信息
        for(int i=0;i<param.size();i++){
            String tmp = param.get(i);
            Map map = new HashMap();
            map.put("departmentId",department.getId());
            map.put("dataRangeType",dataRangeType);
            map.put("createId",department.getUpdateId());
            map.put("updateId",department.getUpdateId());
            map.put("dataRangeId",tmp);
            list.add(map);
        }
        return list;
    }

    //部门基础数据范围信息
    public static Department assembleBaseDepartment(HttpServletRequest request,int id){
        String departmentName = request.getParameter("departmentName");
        String charge = request.getParameter("charge") == null ? "0" : request.getParameter("charge");//部门主管
        Integer chargeId = StringUtils.isBlank(charge) ? 0 : Integer.valueOf(charge);
        String departmentType = request.getParameter("departmentType");
        Department department = new Department();
        department.setName(departmentName);
        department.setParentId(Integer.valueOf(request.getParameter("parentId")));//上级部门ID
        department.setChargeId(chargeId);
        department.setType(departmentType);
        department.setUpdateId(id);
        department.setUpdateTime(new Date());
        return department;
    }

    //根据部门信息返回主管的charge role
    public static int assembleDepartmentRole(List<Role> roleList,Department department){
        String deptChargeRoleString = null;
        if("2".equals(department.getType())){
            deptChargeRoleString = "二级部门经理";
        }else if ("3".equals(department.getType())){
            deptChargeRoleString = "三级部门主管";
        }else if ("4".equals(department.getType())){
            deptChargeRoleString = "四级部门主管";
        }
        int chargeRoleId = 0;
        if(deptChargeRoleString != null) {
            for (int m = 0; m < roleList.size(); m++) {
                Role tmp = roleList.get(m);
                if (deptChargeRoleString.equals(tmp.getName())) {
                    chargeRoleId = tmp.getId();
                }
            }
        }
        return chargeRoleId;
    }
}
