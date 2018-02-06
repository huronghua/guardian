package com.banmatrip.guardian.controller;


import com.banmatrip.guardian.common.PermissionRender;
import com.banmatrip.guardian.core.utils.Md5Utils;
import com.banmatrip.guardian.domain.Department;
import com.banmatrip.guardian.domain.Role;
import com.banmatrip.guardian.domain.User;
import com.banmatrip.guardian.interfaces.Base.MembercacheService;
import com.banmatrip.guardian.interfaces.DepartmentService;
import com.banmatrip.guardian.interfaces.permission.RolePermissionService;
import com.banmatrip.guardian.interfaces.role.RoleService;
import com.banmatrip.guardian.interfaces.user.UserService;
import com.banmatrip.guardian.repository.mapper.membership.DepartmentMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserMapper;
import com.banmatrip.guardian.repository.mapper.rolepermission.RoleMapper;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    PermissionRender permissionRender;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    MembercacheService membercacheService;

    @RequestMapping(value = "/infoEditShow/{id}/{departmentId}/{origin}",method = RequestMethod.GET)
    public String infoEdit(ModelMap model,@PathVariable int id,@PathVariable int departmentId,@PathVariable int origin){
        /**获取目的地，渠道，产品类型并传到前端页面**/
        try {
            model.put("destination",userService.getDestination(id));
            model.put("productType",userService.getProductType(id));
            model.put("platform",userService.getPlatform(id));
            model.put("userId",id);
            model.put("role",userService.getPosition(id));
            model.put("roleType",userService.getRoleType(id));
            model.put("ethnic",userService.getEthnic(id));
            model.put("department",roleService.findAllDep(id));
            model.put("depId",userService.getDepById(id));
            model.put("dataRange",userService.getDataRange(id));
            model.put("user",userService.getUserById(id));
            model.put("depWithEcho",userService.getDepListWithEcho(id));
            model.put("userNameList",userService.getUserNameList());
            model.put("userployeeIdList",userService.getUserEmployeeIdList(id));
            model.put("userEmailList",userService.getUserEmaiList(id));
            model.put("userNameCheck",userMapper.getUserNameCheck(id));
            model.put("userOrangeAcc",userService.getUserOrangeAccountList(id));
            model.put("departmentId",departmentId);
            List<Role> roleList = roleMapper.findAll();
            model.put("roleList",roleList);
            List<Integer> roleListNow = roleMapper.selectRoleListNow(id);
            model.put("roleListNow",roleListNow);
            /**判断用户是否存在，暂时放置，下个版本增添**/
            model.put("resourceType",userService.getResourceType(id));
            model.put("origin",origin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "infoedit";
    }

    @RequestMapping(value = "/infoEditSave",method = RequestMethod.POST)
    public String infoEditSave(ModelMap model, HttpServletRequest request, Principal principal){
        Map<String,Object> param = new HashMap<>();
        Map<String,Object> config = new HashMap<>();
        try {
            param.put("id",request.getParameter("userId"));
            param.put("name",request.getParameter("name"));
            param.put("position",request.getParameter("position"));
            param.put("roleType",request.getParameter("roleTypeList"));
            param.put("ethnic",request.getParameter("ethnicList"));
            param.put("department",request.getParameter("departmentList"));
            param.put("userCellphone",request.getParameter("userCellphone"));
            param.put("email",request.getParameter("email"));
            param.put("employeeId",request.getParameter("employeeId"));
            param.put("account",request.getParameter("orangeAccount"));
            param.put("departmentGroup",request.getParameter("departmentGroup"));
            param.put("roleList",request.getParameter("roleList"));
            config.put("destination",request.getParameter("destinationList"));
            config.put("platform",request.getParameter("platformList"));
            config.put("productType",request.getParameter("productTypeList"));
            config.put("resourceType",request.getParameter("resourceTypeList"));
            config.put("id",request.getParameter("userId"));
            config.put("position",request.getParameter("position"));
            config = userService.checkConf(config);
            Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
            int updateId = (Integer) userMap.get("id");
            param.put("updateId",updateId);
            int deptId = userService.editUser(param,config,Integer.parseInt(request.getParameter("userId")));
            List<Map<String,Object>> list=departmentService.findAll();
            Department department = departmentService.findDepById(deptId);

            /*根据userId查询出orange系统的adminId*/
            Integer adminId = roleService.selectAdminIdByUserId(Integer.parseInt(request.getParameter("userId")));
            List<Integer> adminIdList = new ArrayList<>();
            adminIdList.add(adminId);
            /*插入orange系统的memberchache中通知权限变更*/
            membercacheService.notifyPermissionChangeByAdminId(adminIdList);
            Gson gson=new Gson();
            String listString=gson.toJson(list);
            /*树数据*/
            model.put("list",listString);
            /*初始部门数据*/
            model.put("department",department);
            model.put("departmentId",deptId);
            model.put("departmentName",department.getName());
        } catch (Exception e) {
            e.printStackTrace();
            model.put("fail",e.getMessage());
        }
        /**成员架构功能权限**/
        permissionRender.renderMemberPermission(model,principal.getName());
        String originString = request.getParameter("origin");
        String returnUrl;
        if("1".equals(originString)){
            returnUrl = "membership/main";
        }else {
            List<Map<String, Object>> tree = roleService.initRoleTree();
            Integer id=0;
            String title="";
            if (CollectionUtils.isNotEmpty(tree)&& tree.get(0) !=null&&tree.get(0).get("children")!=null) {
                List<Map<String, Object>> childList = (List<Map<String, Object>>) tree.get(0).get("children");
                id = (Integer) childList.get(0).get("role_id");
                title = String.valueOf(childList.get(0).get("name"));
            }
            model.put("roleId",id);
            model.put("title",title);
            model.put("tree",tree);
            returnUrl = "role/index";
        }
        return returnUrl;
    }
    @RequestMapping(value = "/infoSavaShow/{depId}",method = RequestMethod.GET)
    public String infoSaveShow(ModelMap model,@PathVariable int depId){
        /**获取目的地，渠道，产品类型并传到前端页面**/
        try {
            model.put("destination",userService.getDestination());
            model.put("productType",userService.getProductType());
            model.put("platform",userService.getPlatform());
            model.put("resourceType",userService.getResourceType());
            model.put("role",userService.getDictionary(1));
            model.put("roleType",userService.getDictionary(2));
            model.put("ethnic",userService.getDictionary(7));
            model.put("department",roleService.findAllDep());
            model.put("depWithEcho",userService.getDepListWithEchoDept(depId));
            model.put("userNameList",userService.getUserNameList());
            model.put("userployeeIdList",userService.getUserEmployeeIdList());
            model.put("userEmailList",userService.getUserEmaiList());
            model.put("userNameCheck",userMapper.getNewUserNameCheck());
            model.put("userOrangeAcc",userService.getUserOrangeAccountList());
            model.put("departmentId",depId);
            List<Role> roleList = roleMapper.findAll();
            model.put("roleList",roleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "infoSave";
    }

    @RequestMapping(value = "/infoSave",method = RequestMethod.POST)
    public String infoSave(ModelMap model,HttpServletRequest request,Principal principal){
        Map<String,Object> param = new HashMap<>();
        Map<String,Object> config = new HashMap<>();
        try {
            param.put("name",request.getParameter("name"));
            param.put("position",request.getParameter("position"));
            param.put("roleType",request.getParameter("roleTypeList"));
            param.put("roleList",request.getParameter("roleList"));
            param.put("ethnic",request.getParameter("ethnicList"));
            param.put("department",request.getParameter("departmentList"));
            param.put("userCellphone",request.getParameter("userCellphone"));
            param.put("email",request.getParameter("email"));
            param.put("employeeId",request.getParameter("employeeId"));
            param.put("account",request.getParameter("orangeAccount"));
            /**密码MD5加密**/
            param.put("password", Md5Utils.encodeMD5Hex("123456"));
            config.put("destination",request.getParameter("destinationList"));
            config.put("platform",request.getParameter("platformList"));
            config.put("productType",request.getParameter("productTypeList"));
            config.put("resourceType",request.getParameter("resourceTypeList"));
            config.put("position",request.getParameter("position"));
            config = userService.checkConf(config);
            boolean aa = userService.addUser(param,config);
            List<Integer> depId = stringCastList(request.getParameter("departmentList"));
            List<Map<String,Object>> list=departmentService.findAll();
            int initDepartmentId = depId.get(0) == null ? 1 : depId.get(0);
            Department department = departmentService.findDepById(initDepartmentId);
            Gson gson=new Gson();
            String listString=gson.toJson(list);
            /*树数据*/
            model.put("list",listString);
            /*初始部门数据*/
            model.put("department",department);
            model.put("departmentId",department.getId());
            model.put("departmentName",department.getName());
        } catch (Exception e) {
            e.printStackTrace();
            model.put("fail",e.getMessage());
        }
        /**成员架构功能权限**/
        permissionRender.renderMemberPermission(model,principal.getName());
        return "membership/main";
    }

    @RequestMapping(value = "/name",method = RequestMethod.GET)
    @ResponseBody
    public User tree(HttpServletRequest request, HttpServletResponse response) {
        List<String> nameList = userService.getUserNameList();
        String name = request.getParameter("name");
        Map map = new HashMap();
        User user = new User();
        if (nameList.contains(name))
        {
            map.put("flag",1);
            user.setDeleteFlag("1");
        }else {
            map.put("flag",2);
            user.setDeleteFlag("2");
        }
        return user;
    }

    private List stringCastList(String string){
        List result = new ArrayList();
        String[] temp = string.split(",");
        for (int i = 0;i<temp.length;i++){
            String st = temp[i];
            result.add(Integer.parseInt(st));
        }
        return result;
    }


    @RequestMapping(value = "/queryuser",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> queryUserByName(@RequestParam String name) {
        List<Map<String,Object>> userList=userService.queryUserByName(name);
        return userList;
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public void deleteUser(@RequestParam Integer userId) {
       userService.deleteUserById(userId);
    }

    @RequestMapping(value = "/getDepartmentIndex",method = RequestMethod.GET)
    public String getDepIndex(ModelMap modelMap,@RequestParam Integer departmentId,Integer userId,Principal principal) {
        userService.deleteUserById(userId);
        List<Map<String,Object>> list=departmentService.findAll();
        Department department = departmentService.findDepById(departmentId);
        Gson gson=new Gson();
        String listString=gson.toJson(list);
            /*树数据*/
        modelMap.put("list",listString);
            /*初始部门数据*/
        modelMap.put("department",department);
        modelMap.put("departmentName",department.getName());
        permissionRender.renderMemberPermission(modelMap,principal.getName());
        return "membership/main";
    }

    /*ajax模糊查询公司人员信息*/
    @RequestMapping( method = RequestMethod.GET ,value ="/getUserName")
    @ResponseBody
    public List<Map<String,Object>> getUserName(HttpServletRequest request, HttpServletResponse response){
        String charge = request.getParameter("charge");
        List<Map<String,Object>> userResult;
        if(null == charge || charge == ""){
            userResult = userMapper.selectAllChargeByName();
        }else{
            userResult = userMapper.selectChargeByNameAjax(charge);
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
            }
        }
        return userResult;
    }

    /*用户初始化orange权限*/
    @RequestMapping(method = RequestMethod.GET,value = "/initOrangeAuth")
    @ResponseBody
    public void initOrangeAuth(HttpServletRequest request, HttpServletResponse response){
        String adminId = request.getParameter("adminId") == null ?  "" : request.getParameter("adminId");//admin_account的用户id
        String ethic = request.getParameter("ethic") == null ?  "" : request.getParameter("ethic");//族群
        String roleType = request.getParameter("roleType") == null ?  "" : request.getParameter("roleType");//序列
        userService.initAuth(adminId,ethic,roleType);
    }

    /*获取禁用成员*/
    @RequestMapping(value = "/memberDisableList",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getMemberDisableList(){
        List<Map<String,Object>> memberDisableList=userService.getMemberDisableList();
        return memberDisableList;
    }

    //批量编辑人员
    @RequestMapping(value="/batchEdit/{result}/{departmentId}")
    public String preDelete(Map<String,Object> model, Principal principal, HttpServletRequest request, @PathVariable String result,@PathVariable String departmentId){
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        Map<String,Object> batchEditMap = userService.batchEdit(result,id);
        for (String key : batchEditMap.keySet()) {
            model.put(key,batchEditMap.get(key));
        }
        model.put("departmentId",departmentId);
        return "membership/batchEdit";
    }

    /*批量修改*/
    @RequestMapping(method = RequestMethod.POST, value="/batchMemberEdit")
    public String batchMemberEdit(HttpServletRequest request,Principal principal,ModelMap modelMap,ModelMap permissionMap) throws Exception{
        Map<String,Object> userMap = rolePermissionService.getUserByAccount(principal.getName());
        int id = (Integer) userMap.get("id");
        boolean x = userService.batchMemberEdit(request,id);
        List<Map<String,Object>> list=departmentService.findAll();
        String deptIdString = request.getParameter("departmentId") == null ? "0" : request.getParameter("departmentId");
        Department department = departmentMapper.selectByPrimaryKey(Integer.parseInt(deptIdString));//返回主页table数据
        Gson gson=new Gson();
        String listString=gson.toJson(list);
        /*树数据*/
        modelMap.put("list",listString);
        /*初始部门数据*/
        modelMap.put("department",department);
        modelMap.put("departmentId",Integer.parseInt(deptIdString));
        modelMap.put("departmentName",department.getName());
        modelMap.put("success","批量修改成员信息成功！");
        /**成员架构功能权限**/
        permissionRender.renderMemberPermission(permissionMap,principal.getName());
        return "membership/main";
    }

    /*密码重置*/
    @RequestMapping(method = RequestMethod.GET ,value ="/passwordReset")
    @ResponseBody
    public void passwordReset(HttpServletRequest request){
        Integer id = request.getParameter("userId") == null ? null : Integer.valueOf(request.getParameter("userId"));
        userService.passwordReset(id);
    }
}
