package com.banmatrip.guardian.repository.mapper.membership;

import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.User;
import com.banmatrip.guardian.vo.role.RoleMemberVo;
import io.swagger.models.auth.In;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<Map<String,Object>> getDestination();

    List<Integer> getPlatformParentId();

    Map<String,Object> getPlatformById(@Param("id") int id);

    List<Map<String,Object>> getChildPlatform(@Param("parentId") int parentId);

    List<Map<String,Object>> getProductType();

    void updateUser(@Param("info") Map<String,Object> map);

    List<Map<String,Object>> selectDataRangeById(@Param("id") int id);

    void deleteByUserId(@Param("id") int id);

    int insertUser(@Param("user") Map user);

    List<Integer> selectDepById(@Param("id") int id);

    List<User> selectAllUser();

    List<Map<String,Object>> selectAllUserAccount();

    User selectByPrimaryId(Integer id);

    List<RoleMemberVo> findUserByRoleId(Integer roleId);

    List<RoleMemberVo> getMemberWithoutRole();

    Integer getMemberCountWithoutRole();

    void updatePassword(Map<String,Object> info);

    List<Map<String,Object>> getChildChannelDictionaryByParentId(Integer id);

    User selectByAccount(String account);

    Map<String,Object> selectById(Integer id);

    Integer selectDepartmentGroup(Integer id);

    Integer insertUserByNoId(@Param("user")Map User);

    Integer selectMaxDepId();

    List<String> selectUserNameList();

    List<RoleMemberVo> ajaxSearch(Map<String,Object> map);

    List<Map<String,Object>> getMemberByDepartmentId(Integer id);

    List<Map<String,Object>> getMemberByDepartmentIdNoDepartment();

    List<Map<String,Object>> getMemberByDepartmentIdHasDepartment();

    Integer getPeopleCount(List<Department> list);

    List<Integer> findUserIdByRoleId(Integer roleId);

    List<Integer> findDepartmentIdByRoleId(Integer roleId);

    List<Integer> findDepartmentIdByAccount(String loginAccount);

    String selectPositionIdList(@Param("id") int id);

    String selectRoleTypeIdList(@Param("id") int id);

    String selectEthnicIdList(@Param("id") int id);

    List<Map<String,Object>> queryUserByName(Map<String,Object> param);

    List<Map<String,Object>> selectChargeByName(String charge);

    List<Map<String,Object>> selectChargeByNameAjax(String charge);

    List<String> selectUserEmployeeId();

    List<String> selectUserOrangeAcc();

    List<String> selectUserEmail();

    List<String> selectUserEmployeeIdEx(@Param("id") int id);

    List<String> selectUserOrangeAccEx(@Param("id") int id);

    List<String> selectUserEmailEx(@Param("id") int id);

    List<Map<String,Object>> selectAllChargeByName();

    Integer insertByUserList(List<Map<String,Object>> userList);

    Integer insertOneByUserList(Map<String,Object> tmp);

    Integer insertAdminAcountByUserList(List<Map<String,Object>> userList);

    Integer insertOneAdminAcountByUserList(Map<String,Object> tmp);

    Integer insertSSOContrast(Map<String,Object> tmp);

    void updateDepartmentByUserList(List<Map<String,Object>> userList);

    void deleteUserById(@Param("id") int id);

    List<Map<String,Object>> selectAllAuth();

    void insertInitAuth(Map<String, Object> param);

    List<Map<String,Object>> selectAllDisableMember();

    List<String> getUserNameCheck(int id);

    List<String> getNewUserNameCheck();

    void updateAdminPassword(Map<String, Object> info);

    List<Map<String,Object>> getMemberFromRoot(Integer id);

    List<Map<String,Object>> selectPlatformWhithoutChil();

    void updateOneAdminAcountByUserList(Map user);

    List<Integer> selectAdminAccountById(Map user);

    void passwordRest(Map<String, Object> param);

}