package com.banmatrip.guardian.repository.mapper.membership;

import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.UserRole;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface DepartmentGroupMapper {

    int insert(UserRole record);

    int insertSelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    int insertUserDepartment(@Param("department") List<Integer> department,@Param("id") int id);

    void insertUserDepartmentList(List<Map<String,Object>> departmentList);

    int deleteDepartment(@Param("id") int id);

    List<Map<String,String>> selectForDepartmentName(int departmentId);

    void deleteByDepartment(Department department);

    void insertDepartmentByDepartmentList(List<Map<String,Object>> userList);

    void deleteDepartmentByBatchList(List<Map<String, Object>> memberInfoList);

    void insertDepartmentByBatchList(List<Map<String, Object>> memberInfoList);
}