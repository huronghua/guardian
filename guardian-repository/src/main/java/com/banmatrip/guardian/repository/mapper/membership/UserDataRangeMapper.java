package com.banmatrip.guardian.repository.mapper.membership;

import com.banmatrip.guardian.domain.UserDataRange;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

public interface UserDataRangeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserDataRange record);

    int insertSelective(UserDataRange record);

    UserDataRange selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDataRange record);

    int updateByPrimaryKey(UserDataRange record);

    int deleteByUserId(@Param("userId") Integer id);

    void insertDataRange(@Param("map") Map map);

    List<Integer> selectCheckedData(@Param("userId") int userId,@Param("type") int type);

    void insertDataRangeByDataList(List<Map<String,Object>> dataList);

    void deleteDataRangeByList(List<Map<String, Object>> memberInfoList);

    void deleteDataRangeRoleByList(Map<String, Object> roleParam);

    void updateRoleRangeByList(List<Map<String, Object>> memberInfoList);

    void deleteRoleByUserId(Map<String,Object> roleParamMap);

    void insertRoleByEditInfo(Map<String, Object> roleParam);
}