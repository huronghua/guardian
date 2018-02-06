package com.banmatrip.guardian.interfaces;

import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Miracle Xu
 * @Description: 部门相关接口（包含个人中心与批量上传）
 * @create 2017-09-16 14:00
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public interface DepartmentService {
    Map<String,Object> departmentEdit(int departmentId,int id);
    Map<String,Object> departmentAdd(int departmentId,int id);
    List<Map<String,Object>> getOrderPlatform();
    Department updateDepartment(HttpServletRequest request, int functionType,int id);
    String deleteDepartment(HttpServletRequest request,int id);
    Map<String,Object> personal(int id);
    String personalPasswordChange(HttpServletRequest request,int id);
    void downloadExcelTemplate(HttpServletResponse response) throws IOException;
    List<List<Map>> batchImport(MultipartFile file) throws IOException;
    List<Map<String,Object>> findAll();
    List<Map<String,Object>> getMemberByDepartmentId(Integer id);
    List<Department> findDepartmentByPid(Integer id);
    /*List<Department> findChildDepartment(Integer id);*/
    void findChildDepartment(Integer id,List<Department> tree);
    Department findDepById(int id);
    Integer getIdByName(String name);
}
