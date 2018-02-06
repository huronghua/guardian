package com.banmatrip.guardian.controller;

import com.banmatrip.guardian.common.PermissionRender;
import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.User;
import com.banmatrip.guardian.interfaces.DepartmentService;
import com.banmatrip.guardian.interfaces.permission.RolePermissionService;
import com.banmatrip.guardian.interfaces.user.UserService;
import com.banmatrip.guardian.repository.mapper.membership.DepartmentMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserMapper;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * @author Miracle Xu
 * @Description: 部门管理（包含个人中心与批量上传）
 * @create 2017-09-16 14:11
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Controller
@RequestMapping("/department")
public class DepartmentController  {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    PermissionRender permissionRender;

    //部门信息修改
    @RequestMapping(value="/edit/{departmentId}")
    public String update(Map<String,Object> model, Principal principal, HttpServletRequest request, @PathVariable int departmentId){
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        Map<String,Object> department = departmentService.departmentEdit(departmentId,id);
        for (String key : department.keySet()) {
            model.put(key,department.get(key));
        }
        return "department/edit";
    }

    //部门信息删除
    @RequestMapping(value="/preDelete/{departmentId}")
    public String preDelete(Map<String,Object> model, Principal principal, HttpServletRequest request, @PathVariable int departmentId){
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        Map<String,Object> department = departmentService.departmentEdit(departmentId,id);
        for (String key : department.keySet()) {
            model.put(key,department.get(key));
        }
        return "department/preDelete";
    }

    //部门信息删除
    @RequestMapping(method = RequestMethod.POST, value="/delete")
    public String delete(HttpServletRequest request,Principal principal,Map modelMap,ModelMap permissionMap){
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        String x = departmentService.deleteDepartment(request,id);

        List<Map<String,Object>> list=departmentService.findAll();
        List<Department> departments=departmentService.findDepartmentByPid(1);
        Department department=departments.get(0);
        Gson gson=new Gson();
        String listString=gson.toJson(list);
        /*树数据*/
        modelMap.put("list",listString);
        /*初始部门数据*/
        modelMap.put("department",department);
        modelMap.put("success","部门: "+x+" 删除成功");
        /**成员架构功能权限**/
        permissionRender.renderMemberPermission(permissionMap,principal.getName());
        return "membership/main";
    }

    /*获取user详情*/
    @RequestMapping( method = RequestMethod.GET ,value ="/get_data_dictionary")
    @ResponseBody
    public User getDataDictionary(HttpServletRequest request, HttpServletResponse response){
        Integer id = Integer.valueOf(request.getParameter("charge"));
        User userResult =  userMapper.selectByPrimaryKey(id);
        return userResult;
    }

    /*ajax模糊查询部门主管*/
    @RequestMapping( method = RequestMethod.GET ,value ="/getCharge")
    @ResponseBody
    public List<Map<String,Object>> getCharge(HttpServletRequest request, HttpServletResponse response){
        String charge = request.getParameter("charge");
        List<Map<String,Object>> userResult;
        if(null == charge || charge == ""){
            userResult = userMapper.selectAllChargeByName();
        }else{
            userResult = userMapper.selectChargeByName(charge);
        }
        //过滤空白姓名数据
        if(CollectionUtils.isNotEmpty(userResult)) {
            Iterator iter = userResult.iterator();
            while(iter.hasNext()){
                Map<String,Object> tmp = (Map<String,Object>) iter.next();
                if(null == tmp.get("name") || "".equals(tmp.get("name"))){
                    iter.remove();
                    continue;
                }
                tmp.put("name",tmp.get("name")+" - "+tmp.get("employee_id"));
            }
        }
        return userResult;
    }

    /*修改部门名称唯一性校验*/
    @RequestMapping( method = RequestMethod.GET ,value ="/editDepartmentNameCheck")
    @ResponseBody
    public Map editDepartmentNameCheck(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap();
        Integer id = Integer.valueOf(request.getParameter("departmentId"));
        String departmentName = request.getParameter("departmentName");
        map.put("departmentName",departmentName);
        map.put("id",id);
        int flag = departmentMapper.countByEditDepartmentName(map);
        map.put("flag",flag);
        return map;
    }

    /*新增部门名称唯一性校验*/
    @RequestMapping( method = RequestMethod.GET ,value ="/addDepartmentNameCheck")
    @ResponseBody
    public Map addDepartmentNameCheck(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap();
        String departmentName = request.getParameter("departmentName");
        int flag = departmentMapper.countByAddDepartmentName(departmentName);
        map.put("flag",flag);
        return map;
    }

    /*获取渠道详情*/
    @RequestMapping( method = RequestMethod.GET ,value ="/get_order_platform")
    @ResponseBody
    public List<Map<String,Object>> getOrderPlatform(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> orderPlatformInfo = departmentService.getOrderPlatform();
        return orderPlatformInfo;
    }

    /*更新部门信息*/
    @RequestMapping(method = RequestMethod.POST, value="/update")
    public String update(HttpServletRequest request,Principal principal,ModelMap modelMap) throws Exception{
        int functionType = 1;//修改
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        Department department = departmentService.updateDepartment(request,functionType,id);

        List<Map<String,Object>> list=departmentService.findAll();
        Gson gson=new Gson();
        String listString=gson.toJson(list);
        /*树数据*/
        modelMap.put("list",listString);
        /*初始部门数据*/
        modelMap.put("department",department);
        modelMap.put("departmentId",department.getId());
        modelMap.put("departmentName",department.getName());
        modelMap.put("success","部门: "+department.getName() + " 更新成功！");
        /**成员架构功能权限**/
        permissionRender.renderMemberPermission(modelMap,principal.getName());
        return "membership/main";
    }

    //部门信息新增
    @RequestMapping(value="/preAdd/{departmentId}")
    public String preAdd(Map<String,Object> model,Principal principal,HttpServletRequest request,@PathVariable int departmentId){
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        Map<String,Object> department = departmentService.departmentAdd(departmentId,id);
        for (String key : department.keySet()) {
            model.put(key,department.get(key));
        }
        return "department/preAdd";
    }

    /*新增部门信息*/
    @RequestMapping(method = RequestMethod.POST, value="/add")
    public String add(HttpServletRequest request,Principal principal,Map modelMap,ModelMap permissionMap) throws Exception{
        int functionType = 2;//新增
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        Department department = departmentService.updateDepartment(request,functionType,id);

        List<Map<String,Object>> list=departmentService.findAll();
        Gson gson=new Gson();
        String listString=gson.toJson(list);
        /*树数据*/
        modelMap.put("list",listString);
        /*初始部门数据*/
        modelMap.put("department",department);
        modelMap.put("departmentId",department.getId());
        modelMap.put("departmentName",department.getName());
        modelMap.put("success","部门: "+department.getName() +  " 新增成功！");
        /**成员架构功能权限**/
        permissionRender.renderMemberPermission(permissionMap,principal.getName());
        return "membership/main";
    }

    //个人中心
    @RequestMapping(value="/personal")
    public String personal(Map<String,Object> model, Principal principal){
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        Map<String,Object> department = departmentService.personal(id);
        for (String key : department.keySet()) {
            model.put(key,department.get(key));
        }
        return "department/personal";
    }

    //个人中心-修改密码
    @RequestMapping(value="/personalPasswordChange")
    public String personalPasswordChange(Map<String,Object> model,HttpServletRequest request,Principal principal){
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        departmentService.personalPasswordChange(request,id);
        Map<String,Object> personal = departmentService.personal(id);
        for (String key : personal.keySet()) {
            model.put(key,personal.get(key));
        }
        return "department/personal";
    }

    @RequestMapping(value = "/batchImport")
    public String batchImport( HttpServletRequest request, HttpServletResponse response) {
        return "department/batchImport";
    }

    /*模板文件下载*/
    @RequestMapping(value = "/downloadTemplate")
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        departmentService.downloadExcelTemplate(response);
    }

    /*批量导入*/
    @RequestMapping(method = RequestMethod.POST, value="/batchImportUser")
    public String batchImportUser(@RequestParam(value="file") MultipartFile file,Map modelMap,ModelMap permissionMap,Principal principal) throws IOException{
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        //判断文件是否为空
        if(file==null) return null;
        //获取文件名
        String name=file.getOriginalFilename();
        //判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(name==null || ("").equals(name) && size==0) {
            return null;
        }
        //批量导入
        try {
            List<List<Map>> batchImport = departmentService.batchImport(file);
            if(batchImport.size()>0) {
                userService.batchAddUser(batchImport,id);//批量新增用户
            }else{
                modelMap.put("fail","excel无数据！");
            }
        }catch (Exception e){
            modelMap.put("fail",e.getMessage());
        }
        List<Map<String,Object>> list=departmentService.findAll();
        List<Department> departments=departmentService.findDepartmentByPid(1);
        Department department=departments.get(0);
        Gson gson=new Gson();
        String listString=gson.toJson(list);
        /*树数据*/
        modelMap.put("list",listString);
        /*初始部门数据*/
        modelMap.put("department",department);
        modelMap.put("departmentName",department.getName());
        modelMap.put("success","批量导入成功！");
        /**成员架构功能权限**/
        permissionRender.renderMemberPermission(permissionMap,principal.getName());
        return "membership/main";
    }

    /*成员信息主页入口*/
    @RequestMapping(value = "/index")
    public String getTree(ModelMap modelMap,Principal principal){
        List<Map<String,Object>> list=departmentService.findAll();
        Integer pid=departmentService.getIdByName("上海歌晨信息技术有限公司");
        List<Department> departments=new ArrayList<Department>();
        if(null!=pid) {
            departments = departmentService.findDepartmentByPid(pid);
        }
        Department department=new Department();
        if (CollectionUtils.isNotEmpty(departments)) {
            department = departmentMapper.selectByPrimaryKey(0);//主页
        }
        Gson gson=new Gson();
        String listString=gson.toJson(list);
        /*树数据*/
        modelMap.put("list",listString);
        /*初始部门数据*/
        modelMap.put("department",department);
        modelMap.put("departmentId",department.getId());
        modelMap.put("departmentName",department.getName());
        /**成员架构功能权限**/
        permissionRender.renderMemberPermission(modelMap,principal.getName());
        return "membership/main";
    }


    /*获取部门成员*/
    @RequestMapping(value = "/member",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getMemberByDepartmentId(@RequestParam Integer department_id) throws Exception{
        List<Map<String,Object>> userList = departmentService.getMemberByDepartmentId(department_id);
        return userList;
    }
}
