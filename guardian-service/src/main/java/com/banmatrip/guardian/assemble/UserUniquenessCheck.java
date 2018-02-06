package com.banmatrip.guardian.assemble;

import com.banmatrip.guardian.domain.User;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Miracle Xu
 * @Description:新增用户前唯一性校验
 * @create 2017-09-28 13:37
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class UserUniquenessCheck {
    public static Boolean userAssemble(List<User> userList,Map<String,Object> user) throws RuntimeException{
        if(CollectionUtils.isNotEmpty(userList)) {
            List<String> accountList = new ArrayList();//登录账号
            List<String> emailList = new ArrayList();//邮箱
            List<String> nameList = new ArrayList();//姓名
            for (int i = 0; i < userList.size(); i++) {
                User tmp = userList.get(i);
                if(!"".equals(tmp.getAccount())) {
                    accountList.add(tmp.getAccount());
                }
                if(!"".equals(tmp.getEmail())) {
                    emailList.add(tmp.getEmail());
                }
                if(!"".equals(tmp.getName())) {
                    nameList.add(tmp.getName());
                }
            }
            if (null!=user.get("account")){
                if(accountList.contains(user.get("account"))) {
                    throw new RuntimeException("系统登录账号重复！");
                }else if(emailList.contains(user.get("email"))){
                    throw new RuntimeException("邮箱重复！");
                }else if(nameList.contains(user.get("name"))) {
                    throw new RuntimeException("姓名重复！");
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    public static Boolean userAccountAssemble(List<Map<String,Object>> userAccountList,Map<String,Object> user) throws RuntimeException{
        if(CollectionUtils.isNotEmpty(userAccountList)) {
            List<String> accountList = new ArrayList();//登录账号
            List<String> emailList = new ArrayList();//邮箱
            List<String> nameList = new ArrayList();//姓名
            for (int i = 0; i < userAccountList.size(); i++) {
                Map<String,Object> tmp = userAccountList.get(i);
                if(!"".equals(tmp.get("username"))) {
                    accountList.add((String) tmp.get("username"));
                }
                if(!"".equals(tmp.get("email"))) {
                    emailList.add((String) tmp.get("email"));
                }
                if(!"".equals(tmp.get("name"))) {
                    nameList.add((String) tmp.get("name"));
                }
            }
            if (null!=user.get("account")){
                if(accountList.contains(user.get("account"))){
                    throw new RuntimeException("登录账号重复！");
                }else if(emailList.contains(user.get("email"))){
                    throw new RuntimeException("邮箱重复！");
                }else if(nameList.contains(user.get("name"))) {
                    throw new RuntimeException("姓名重复！");
                }else {
                    return true;
                }
            }
        }
        return true;
    }
}
