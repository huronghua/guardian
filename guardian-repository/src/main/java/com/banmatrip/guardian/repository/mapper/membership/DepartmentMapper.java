package com.banmatrip.guardian.repository.mapper.membership;

import com.banmatrip.guardian.domain.Department;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    int deleteById(Department department);

    int insertByDepartment(Department department);

    List<Integer> selectByDepartmentName(Map<String,Object> param);

    List<Department> selectDepartmentByGroupId(Integer departmentGroupId);

    List<Map<String,Object>> findDepChild(@Param("id") int id);

    List<Map<String,Object>> selectDepTree();

    List<Department> findChild(Integer id);

    List<Department> selectAllDepartment();

    List<Map<String,Object>> findAll();

    List<Integer> selectParentIdById(@Param("id") int id);

    int countByEditDepartmentName(Map map);

    int countByAddDepartmentName(@Param("departmentName") String departmentName);

    Integer getIdByName(String name);

    List<Map<String,Object>> selectDepartmentById(List<Integer> memberIdList);

    int countAllChargeNew(Map<String,Object> chargeRole);

    int countAllChargeOld(Map<String, Object> chargeRole);

    /**
     * 获取部分最大层级
     * @return
     */
    Integer selectMaxDepartmentType();

    /**
     * 获取各个部门人数
     * @return
     */
    List<Map<String,Object>> selectDepartmentUsrCount();
}