package com.banmatrip.guardian.interfaces.user;

import com.banmatrip.guardian.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by banma on 2017/9/17.
 */
public interface UserService {

    List<Map<String,Object>> getDestination(int userId);

    List<Map<String,Object>> getProductType(int userId);

    List<Map<String,Object>> getPlatform(int userId);

    void updateUser(Map map);

    List<Integer> getDepById(int id);

    List<Map<String,Object>> getDataRange(int id);

    void deleteUserAndDataRange(int id);

    int insertUserAndDataRange(Map user, Map userDataRange);

    int editUser(Map param, Map config, int id);

    List<Map<String,Object>> getDictionary(int code);

    User getUserById(int id);

    List<Map<String,Object>> getResourceType(int id);

    List<Map<String,Object>> getDestination();

    List<Map<String,Object>> getProductType();

    List<Map<String,Object>> getPlatform();

    List<Map<String,Object>> getResourceType();

    boolean addUser(Map user, Map conf);

    boolean batchAddUser(List<List<Map>> list,int id);

    List<String> getUserNameList();

    List<Map<String,Object>> getDepListWithEcho(int id);

    List<Map<String,Object>> getDepListWithEchoDept(int deptId);

    List<Map<String,Object>> getPosition(int id);

    List<Map<String,Object>> getRoleType(int id);

    List<Map<String,Object>> getEthnic(int id);


    List<Object> getDepListWithEchoParentId(int id);

    List<Map<String,Object>> getDepListWithEchoNowId(int departmentId);

    List<Map<String,Object>> queryUserByName(String name);

    List<String> getUserEmployeeIdList();

    List<String> getUserEmaiList();

    List<String> getUserOrangeAccountList();

    List<String> getUserEmployeeIdList(int id);

    List<String> getUserEmaiList(int id);

    List<String> getUserOrangeAccountList(int id);

    void deleteUserById(int id);

    void initAuth(String adminId,String ethnic,String roleType);

    List<Map<String,Object>> getMemberDisableList();

    Map<String,Object> batchEdit(String result,int id);

    boolean batchMemberEdit(HttpServletRequest request,Integer id);

    Map<String,Object> checkConf(Map<String,Object> config);

    void passwordReset(Integer id);
}
