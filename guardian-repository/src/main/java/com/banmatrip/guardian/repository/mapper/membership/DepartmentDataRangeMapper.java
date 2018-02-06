package com.banmatrip.guardian.repository.mapper.membership;

import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.DepartmentDataRange;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentDataRangeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentDataRange record);

    int insertSelective(DepartmentDataRange record);

    DepartmentDataRange selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartmentDataRange record);

    int updateByPrimaryKey(DepartmentDataRange record);

    List<DepartmentDataRange> selectByDepartmentId(@Param(value = "departmentId") int departmentId);

    void updateByList(List productType);

    void deleteByDepartmentId(int departmentId);

    void deleteByDepartment(Department department);

    List<Integer> selectTagNow(int departmentId);
    List<Integer> selectOrderPlatformNow(int departmentId);
    List<Integer> selectProductOrderTagNow(int departmentId);
    List<Integer> selectResourceTypeNow(int departmentId);
}