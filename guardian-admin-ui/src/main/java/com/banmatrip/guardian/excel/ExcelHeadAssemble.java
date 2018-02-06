package com.banmatrip.guardian.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelHeadAssemble {
    //将数据库查出来的字段转换为中文显示
    //产品总表表头
    public static List<String> alertOrderDetailHeadAssemble(Map<String, Object> map){
        List<String> resultLine = new ArrayList();
            for (String key : map.keySet()){
                if("roleName".equals(key)){
                    key = "角色名称";
                }
                if("memberName".equals(key)){
                    key = "成员名称";
                }
                if("departmentName".equals(key)){
                    key = "部门";
                }
                if("position".equals(key)){
                    key = "职位";
                }
                if("email".equals(key)){
                    key = "企业邮箱";
                }
                if("employeeId".equals(key)){
                    key = "工号";
                }
                if("cellphone".equals(key)){
                    key = "手机号";
                }
                resultLine.add(key);
            }
            return resultLine;
    }
}
