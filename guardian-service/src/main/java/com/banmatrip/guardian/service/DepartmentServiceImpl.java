package com.banmatrip.guardian.service;

import com.banmatrip.guardian.assemble.DepartmentAssemble;
import com.banmatrip.guardian.assemble.UserAssemble;
import com.banmatrip.guardian.core.utils.Md5Utils;
import com.banmatrip.guardian.core.utils.ReadExcel;
import com.banmatrip.guardian.core.utils.StringUtil;
import com.banmatrip.guardian.domain.*;
import com.banmatrip.guardian.interfaces.Base.BaseService;
import com.banmatrip.guardian.interfaces.DepartmentService;
import com.banmatrip.guardian.interfaces.user.UserService;
import com.banmatrip.guardian.repository.mapper.common.OrderPlatformMapper;
import com.banmatrip.guardian.repository.mapper.common.ProductOrderTagMapper;
import com.banmatrip.guardian.repository.mapper.common.TagMapper;
import com.banmatrip.guardian.repository.mapper.membership.DepartmentDataRangeMapper;
import com.banmatrip.guardian.repository.mapper.membership.DepartmentGroupMapper;
import com.banmatrip.guardian.repository.mapper.membership.DepartmentMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserMapper;
import com.banmatrip.guardian.repository.mapper.rolepermission.DictionaryMapper;
import com.banmatrip.guardian.repository.mapper.rolepermission.RoleMapper;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Miracle Xu
 * @Description: ：部门相关实现类（包含个人中心与批量上传）
 * @create 2017-09-16 14:13
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    BaseService baseService;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    OrderPlatformMapper orderPlatformMapper;
    @Autowired
    ProductOrderTagMapper productOrderTagMapper;
    @Autowired
    DepartmentDataRangeMapper departmentDataRangeMapper;
    @Autowired
    DictionaryMapper dictionaryMapper;
    @Autowired
    DepartmentGroupMapper departmentGroupMapper;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public Map<String,Object> departmentEdit(int departmentId,int id) throws RuntimeException{
        Map<String,Object> model = new HashMap();
        Gson gson = new Gson();
        try {
            Department department = departmentMapper.selectByPrimaryKey(departmentId);//查询部门信息
            Department parentDepartment = null;
            if(null != department) {
                parentDepartment = departmentMapper.selectByPrimaryKey(department.getParentId());//获取上级部门信息
            }
            List departmentList = userService.getDepListWithEchoParentId(departmentId);
            List<User> user = userMapper.selectAllUser();//获取所有人员信息

            List<Tag> tag = tagMapper.selectAllTag();//目的地
            List<Map<String,Object>> orderPlatform = userService.getPlatform();//渠道
            List<Integer> orderPlatformNow = departmentDataRangeMapper.selectOrderPlatformNow(departmentId);
            if (orderPlatformNow!=null&&orderPlatformNow.size()==1&&orderPlatformNow.get(0)==-999){
                orderPlatformNow.clear();
                orderPlatformNow = dictionaryMapper.selectPlatIdList();
            }
            List<Map<String,Object>> realDep = baseService.orderPlatformShow(orderPlatform,orderPlatformNow);
            List<ProductOrderTag> productOrderTag = productOrderTagMapper.selectAllProductOrderTag();//产品类型
            List<Map<String,Object>> resourceType = dictionaryMapper.selectAllResourceType();//资源类型
            List<Integer> tagNow = departmentDataRangeMapper.selectTagNow(departmentId);
            if (tagNow !=null && tagNow.size()==1 && tagNow.get(0)==-999){
                tagNow.clear();
                tagNow = dictionaryMapper.selectDesIdList();
            }

            List<Integer> productOrderTagNow = departmentDataRangeMapper.selectProductOrderTagNow(departmentId);
            if (productOrderTagNow!=null && productOrderTagNow.size()==1 && productOrderTagNow.get(0)==-999){
                productOrderTagNow.clear();
                productOrderTagNow = dictionaryMapper.selectProIdList();
            }
            List<Integer> resourceTypeNow = departmentDataRangeMapper.selectResourceTypeNow(departmentId);
            if (resourceTypeNow!=null&&resourceTypeNow.size()==1&&resourceTypeNow.get(0)==-999){
                resourceTypeNow.clear();
                resourceTypeNow = dictionaryMapper.selectResIdList();
            }

            List<Map<String,Object>> departmentType = dictionaryMapper.selectAllDepartmentType();//获取所有部门类型
            if(null != department){
                model.put("departmentId",department.getId());//当前操作部门ID
                model.put("chargeIdNow",department.getChargeId());//当前主管ID
                User userInfo = userMapper.selectByPrimaryKey(department.getChargeId());
                String chargeNow = null;
                if(null != userInfo){
                    chargeNow = userInfo.getName();
                    model.put("chargeEmployeeNow",userInfo.getEmployeeId());//主管工号显示
                }
                model.put("chargeNow",chargeNow);
                model.put("departmentName",department.getName());
                model.put("parentId",department.getParentId());
                model.put("employee", department.getChargeId());//主管Id
                model.put("type", department.getType());//部门类型
            }
            if(null != parentDepartment){
                model.put("parentDepartmentName",parentDepartment.getName());
            }
            model.put("chargeInfo",gson.toJson(user));
            model.put("department",department);
            model.put("parentDepartment",parentDepartment);

            model.put("tag",tag);
            model.put("productOrderTag",productOrderTag);
            model.put("resourceType",resourceType);
            model.put("tagNow",tagNow);
            model.put("productOrderTagNow",productOrderTagNow);
            model.put("resourceTypeNow",resourceTypeNow);
            model.put("platform",realDep);

            model.put("departmentType",departmentType);
            model.put("depWithEcho",departmentList);
        }catch (Exception e){
            throw new RuntimeException("部门数据查询异常"+e.getMessage());
        }
        return model;
    }

    @Override
    public Map<String, Object> departmentAdd(int departmentId,int id) throws RuntimeException{
        Map<String,Object> model = new HashMap();
        Gson gson = new Gson();
        try {
            Department department = departmentMapper.selectByPrimaryKey(departmentId);//查询部门信息
            List departmentList = userService.getDepListWithEchoNowId(departmentId);
            List<User> user = userMapper.selectAllUser();//获取所有人员信息
            List<Tag> tag = tagMapper.selectAllTag();//目的地
            List<Map<String,Object>> orderPlatform = userService.getPlatform();//渠道
            List<Integer> orderPlatformNow = new ArrayList();
            List<Map<String,Object>> realDep = baseService.orderPlatformShow(orderPlatform,orderPlatformNow);
            List<ProductOrderTag> productOrderTag = productOrderTagMapper.selectAllProductOrderTag();//产品类型
            List<Map<String,Object>> resourceType = dictionaryMapper.selectAllResourceType();//资源类型
            List<Map<String,Object>> departmentType = dictionaryMapper.selectAllDepartmentType();//获取所有部门类型
            if(null != department){
                model.put("departmentName",department.getName());
                model.put("parentDepartmentName",department.getName());
                model.put("parentId",department.getParentId());
                model.put("departmentId",department.getId());
                model.put("parentId",department.getId());
                model.put("employee", department.getChargeId());
            }
            model.put("department",department);
            model.put("chargeInfo",gson.toJson(user));
            model.put("tag",tag);
            model.put("platform",realDep);
            model.put("productOrderTag",productOrderTag);
            model.put("resourceType",resourceType);
            model.put("departmentType",departmentType);
            model.put("depWithEcho",departmentList);
        }catch (Exception e){
            throw new RuntimeException("部门数据查询异常"+e.getMessage());
        }
        return model;
    }

    @Override
    public List<Map<String, Object>> getOrderPlatform() throws RuntimeException{
        List<Map<String,Object>> orderPlatformInfo = new ArrayList();
        try{
            List<OrderPlatform> orderPlatform = orderPlatformMapper.selectAllOrderPlatform();//渠道
            if(!orderPlatform.isEmpty()){
                for(OrderPlatform parentchannel : orderPlatform){
                    Integer parentId = parentchannel.getId();
                    List<OrderPlatform> childChannel = orderPlatformMapper.selectAllOrderPlatformByParentId(parentId);
                    Map map =new HashMap();
                    map.put("name",parentchannel.getName());
                    map.put("id",parentchannel.getId());
                    if (null != childChannel) {
                        map.put("children",childChannel);
                        orderPlatformInfo.add(map);
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException("获取渠道数据异常"+e.getMessage());
        }
        return orderPlatformInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = RuntimeException.class,timeout = 60)
    public Department updateDepartment(HttpServletRequest request,int functionType,int id) throws RuntimeException{
        //部门基础信息
        Department department = DepartmentAssemble.assembleBaseDepartment(request,id);
        List<Role> roleList = roleMapper.findAllForCheck();
        int chargeRoleId = DepartmentAssemble.assembleDepartmentRole(roleList,department);
        Map<String,Object> chargeRole = new HashMap();
        chargeRole.put("updateId",id);
        chargeRole.put("chargeId",department.getChargeId());
        chargeRole.put("chargeRoleId",chargeRoleId);
        if(1 == functionType){    //修改部门
            String departmentId = request.getParameter("departmentId");//部门ID
            department.setId(Integer.valueOf(departmentId));
            //修改部门时判断，如果是二级部门或者三级部门则先删除主管与相关角色，再将主管和相关角色存入user_role
            //获取修改前的部门旧数据
            Department departmentOld = departmentMapper.selectByPrimaryKey(department.getId());
            int oldChargeRoleId = DepartmentAssemble.assembleDepartmentRole(roleList,departmentOld);
            chargeRole.put("oldChargeId",departmentOld.getChargeId());
            chargeRole.put("oldChargeRoleId",oldChargeRoleId);
            chargeRole.put("departmentId",department.getId());
            //先判断新旧主管是否已存在该（主管）角色
            int totalExistChargeOld = departmentMapper.countAllChargeOld(chargeRole);
            if(totalExistChargeOld == 0){
                roleMapper.deleteChargeRole(chargeRole);
            }
            int totalExistChargeNew = departmentMapper.countAllChargeNew(chargeRole);
            if(totalExistChargeNew == 0){
                roleMapper.insertChargeRole(chargeRole);
            }
            departmentMapper.updateByPrimaryKeySelective(department);
        }else if(2 == functionType){        //新增部门
            departmentMapper.insertByDepartment(department);
            //根据新增部门的层级，将主管和相关角色存入user_role
            //先判断该主管是否已存在该（主管）角色
            int totalExistCharge = departmentMapper.countAllChargeNew(chargeRole);
            if(totalExistCharge == 0) {
                roleMapper.insertChargeRole(chargeRole);
            }
        }
        //部门数据范围信息
        List<String> productTypeInfo = request.getParameter("productType") == null ? null: StringUtil.addSingleQuoteToList(request.getParameter("productType"));
        List<String> orderPlatformInfo = request.getParameter("orderPlatform") == null ? null: StringUtil.addSingleQuoteToList(request.getParameter("orderPlatform"));
        List<String> tagInfo = request.getParameter("tag") == null ? null: StringUtil.addSingleQuoteToList(request.getParameter("tag"));
        List<String> resourceTypeInfo = request.getParameter("resourceType") == null ? null: StringUtil.addSingleQuoteToList(request.getParameter("resourceType"));
        if (tagInfo != null&&tagInfo.size() == dictionaryMapper.selectDesCount()){
            tagInfo.clear();
            tagInfo.add("-999");
        }
        if (orderPlatformInfo != null&&orderPlatformInfo.size() == dictionaryMapper.selectPlatCount()){
            orderPlatformInfo.clear();
            orderPlatformInfo.add("-999");
        }
        if (productTypeInfo != null&&productTypeInfo.size() == dictionaryMapper.selectProCount()){
            productTypeInfo.clear();
            productTypeInfo.add("-999");
        }
        if (resourceTypeInfo != null&&resourceTypeInfo.size() == dictionaryMapper.selectResCount()){
            resourceTypeInfo.clear();
            resourceTypeInfo.add("-999");
        }
        try{
            departmentDataRangeMapper.deleteByDepartmentId(department.getId());//先删除后新增
            if(null != productTypeInfo){
                List<Map<String,Object>> productType = DepartmentAssemble.assembleDepartment(productTypeInfo,department,"3");
                departmentDataRangeMapper.updateByList(productType);
            }
            if(null != orderPlatformInfo){
                List<Map<String,Object>> orderPlatform = DepartmentAssemble.assembleDepartment(orderPlatformInfo,department,"2");
                departmentDataRangeMapper.updateByList(orderPlatform);
            }
            if(null != tagInfo){
                List<Map<String,Object>> tag = DepartmentAssemble.assembleDepartment(tagInfo,department,"1");
                departmentDataRangeMapper.updateByList(tag);
            }
            if(null != resourceTypeInfo){
                List<Map<String,Object>> resourceType = DepartmentAssemble.assembleDepartment(resourceTypeInfo,department,"4");
                departmentDataRangeMapper.updateByList(resourceType);
            }
        }catch (Exception e){
            throw new RuntimeException("修改/新增部门异常"+e.getMessage());
        }
        return department;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = RuntimeException.class,timeout = 60)
    public String deleteDepartment(HttpServletRequest request,int id) throws RuntimeException{
        Department department;
        try {
            //逻辑删除部门信息
            department = DepartmentAssemble.assembleBaseDepartment(request,id);
            department.setDeleteFlag(1);//删除标志位，0未删除，1已删除
            String departmentId = request.getParameter("departmentId");//部门ID
            department.setId(Integer.valueOf(departmentId));
            departmentMapper.deleteById(department);
            //同步逻辑删除部门数据范围
            departmentDataRangeMapper.deleteByDepartment(department);
            //该部门下面的人员全部移除本部门
            departmentGroupMapper.deleteByDepartment(department);
        }catch (Exception e){
            throw new RuntimeException("删除部门出现异常"+e.getMessage());
        }
        return department.getName()+"  success";
    }

    @Override
    public Map<String, Object> personal(int id) {
        Map<String,Object> map = new HashMap();
        try {
            //获取个人信息
            User personalInfo = userMapper.selectByPrimaryKey(id);
            if (null != personalInfo) {
                List<String> roleTypeList = StringUtil.addSingleQuoteToList(personalInfo.getRoleType());
                String roleType = "";
                if(CollectionUtils.isNotEmpty(roleTypeList)){
                    List<String> roleTypeStringList = dictionaryMapper.selectForRoleType(roleTypeList);
                    roleType = StringUtil.listToString(roleTypeStringList, ',');
                }
                String position = dictionaryMapper.selectForPosition(personalInfo.getPositionId());
                String ethnic = dictionaryMapper.selectEthnicById(personalInfo.getEthnic());
                List<Map<String, String>> departmentName = departmentGroupMapper.selectForDepartmentName(personalInfo.getDepartmentId());
                StringBuffer allDepartmentName = new StringBuffer();
                if (CollectionUtils.isNotEmpty(departmentName)) {
                    for (int i = 0; i < departmentName.size(); i++) {
                        allDepartmentName.append(departmentName.get(i).get("name")).append(" / ");
                    }
                } else {
                    allDepartmentName.append(" / ");
                }
                map.put("user", personalInfo);
                map.put("position", position);
                map.put("roleType", roleType);
                map.put("ethnic", ethnic);
                map.put("allDepartmentName", allDepartmentName.substring(0, allDepartmentName.length() - 2).toString());
            }
        }catch (Exception e){
            throw new RuntimeException("查询个人信息异常"+e.getMessage());
        }
        return map;
    }

    @Override
    public String personalPasswordChange(HttpServletRequest request,int id) {
        String passwordTmp = request.getParameter("password");
        String password = Md5Utils.encodeMD5Hex(passwordTmp);//转Md5摘要存储
        Map<String,Object> info = new HashMap();
        info.put("password",password);
        info.put("id",id);
        User user = userMapper.selectByPrimaryKey(id);
        info.put("username",user.getAccount());
        try {
            userMapper.updatePassword(info);//更新密码
            userMapper.updateAdminPassword(info);//更新密码
        }catch (Exception e){
            throw new RuntimeException("更新密码异常"+e.getMessage());
        }
        return "success";
    }

    @Override
    public void downloadExcelTemplate(HttpServletResponse response) throws IOException {

        String fileName = "batchImportTemplate.xlsx";//下载文件名
        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        InputStream stream = getClass().getClassLoader().getResourceAsStream("batchImportTemplate.xlsx");
        response.setContentType("multipart/form-data");// 设置文件ContentType类型，自动判断下载文件类型
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.addHeader("Content-Type", "application/vnd.ms-excel");
        OutputStream out;
        try {
            InputStream inputStream =  stream;
            out = response.getOutputStream();//通过response获取OutputStream对象(out)
            byte[] buffer = new byte[512];
            int b = inputStream.read(buffer);
            while (b != -1) {   //写到输出流(out)中
                out.write(buffer, 0, b);
                b = inputStream.read(buffer);
            }
            inputStream.close();
            out.close();
            out.flush();
        } catch (Exception e) {
            throw new IOException("模板文件下载异常"+e.getLocalizedMessage());
        }
    }

    @Override
    public List<List<Map>> batchImport(MultipartFile file) throws IOException,RuntimeException {

        ReadExcel readExcel=new ReadExcel();//创建处理excel
        List<Map<String,Object>> customerList;//解析excel,把spring文件上传的MultipartFile转换成输入流
        try {
            customerList = readExcel.getExcelInfo(file.getInputStream());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        List<Map<String,Object>> userList = new ArrayList();
        List<List<Map>> returnUserList = new ArrayList();
        if(customerList != null){
            userList = UserAssemble.userAssemble(customerList);//数据组装
            //转制数据，将部分String数据转为可存储的int数据
            try {
                List<Department> departments = departmentMapper.selectAllDepartment();
                for (int i = 0; i < userList.size(); i++) {
                    List<Map> oneUserInfo = new ArrayList();
                    Map<String,Object> listInfo = new HashMap();
                    Map<String, Object> tmp = userList.get(i);
                    Map user = (Map) tmp.get("user");//获取user基础信息
                    int positionId = dictionaryMapper.selectByPositionName((String) user.get("position"));//岗位转换为岗位ID
                    String roleType = dictionaryMapper.selectByRoleType((String) user.get("roleType"));//序列转换为序列ID
                    String ethnic = dictionaryMapper.selectByEthnic((String) user.get("ethnic"));//族群转换为族群ID
                    List<String> roleTransList = StringUtil.addSingleQuoteToList((String) user.get("role"));//多个角色分割
                    List<String> role = dictionaryMapper.selectByRole(roleTransList);//角色转换为角色ID
                    user.put("position", positionId);//岗位
                    user.put("roleType", roleType);//序列
                    user.put("ethnic", ethnic);//族群
                    user.put("role", role);//角色

                    List<String> departmentString = (List<String>) tmp.get("department");//部门String信息
                    String department1String = tmp.get("department_1") == null ? "" : (String) tmp.get("department_1");//一级部门
                    String department2String = tmp.get("department_2") == null ? "" : (String) tmp.get("department_2");;//二级部门
                    String department3String = tmp.get("department_3") == null ? "" : (String) tmp.get("department_3");;//三级部门
                    List<String> destinationString = (List<String>) tmp.get("destination");//目的地String信息
                    List<String> platformString = (List<String>) tmp.get("platform");//渠道String信息
                    List<String> productTypeString = (List<String>) tmp.get("productType");//产品类型String信息
                    List<String> resourceTypeString = (List<String>) tmp.get("resourceType");//资源类型String信息

                    List<Integer> department = new ArrayList();
                    List<Integer> destination = null;
                    List<Integer> platform = null;
                    List<Integer> productType = null;
                    List<String> resourceType = null;
                     int dept_1 = 0;
                     int dept_2 = 0;
                     int dept_3 = 0;
                     for(int m = 0;m<departments.size();m++){
                         Department deptTmp = departments.get(m);
                         if(department1String.equals(deptTmp.getName())){
                             dept_1 = deptTmp.getId();
                         }
                         if(department2String.equals(deptTmp.getName())){
                             dept_2 = deptTmp.getId();
                         }
                         if(department3String.equals(deptTmp.getName())){
                             dept_3 = deptTmp.getId();
                         }
                     }
                     //根据一级部门二级部门三级部门获取四级部门
                    if(CollectionUtils.isNotEmpty(departmentString) && (dept_1 != 0) && (dept_2 != 0) && (dept_3 != 0)) {
                         Map<String,Object> param = new HashMap();
                         param.put("list",departmentString);
                         param.put("dept_1",dept_1);
                         param.put("dept_2",dept_2);
                         param.put("dept_3",dept_3);
                        department = departmentMapper.selectByDepartmentName(param);
                    }else {
                        if(dept_1 != 0){
                            if(dept_2 != 0){
                                if(dept_3 != 0){
                                    department.add(Integer.valueOf(dept_3));//不存在四级部门，则直接存入三级部门
                                }else {
                                    department.add(Integer.valueOf(dept_2));//不存在三级部门，则直接存入二级部门
                                }
                            }else {
                                department.add(Integer.valueOf(dept_1));//不存在二级部门，则直接存入一级部门
                            }
                        }else {
                            throw new RuntimeException("部门数据缺失，请检查导入数据！");//若一级部门都不存在则直接抛错
                        }
                    }
                    if(CollectionUtils.isNotEmpty(destinationString)){
                         destination = tagMapper.selectByDestinationString(destinationString);
                    }
                    if(CollectionUtils.isNotEmpty(platformString)){
                         platform = orderPlatformMapper.selectByPlatformString(platformString);
                    }
                    if(CollectionUtils.isNotEmpty(productTypeString)){
                         productType = productOrderTagMapper.selectByProductTypeString(productTypeString);
                    }
                    if(CollectionUtils.isNotEmpty(resourceTypeString)){
                         resourceType = dictionaryMapper.selectByResourceTypeString(resourceTypeString);
                    }

                    user.put("department", department);
                    listInfo.put("destination", destination);
                    listInfo.put("platform", platform);
                    listInfo.put("productType", productType);
                    listInfo.put("resourceType", resourceType);
                    oneUserInfo.add(user);//user信息，包括String的department
                    oneUserInfo.add(listInfo);//四个list
                    returnUserList.add(oneUserInfo);
                }
            }catch (Exception e){
                throw new RuntimeException("上传的excel数据异常"+e.getMessage());
            }
        }
        return returnUserList;
    }

    /*获取下一级部门*/
    @Override
    public List<Department> findDepartmentByPid(Integer id){
        try {
            List<Department> departments= departmentMapper.findChild(id);
            return departments;
        }catch (Exception e){
            throw new RuntimeException("获取下一级部门异常"+e.getMessage());
        }
    }


    /*获取部门树数据*/
    @Override
    public List<Map<String,Object>> findAll(){
        try {
            //单个部门人数
            List<Map<String, Object>> list = departmentMapper.selectDepartmentUsrCount();
            //返回部门结果list
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            //部门最大层级
            Integer maxDepartmentType=departmentMapper.selectMaxDepartmentType();

            if(maxDepartmentType !=null && maxDepartmentType.intValue()>0) {
                //按部门层级顺序遍历部门，从层级最低的开始
                for (int i = maxDepartmentType; i >= 0; i--) {
                    for (Map<String, Object> departmentInfo : list
                         ) {
                        if(String.valueOf(i).equals(departmentInfo.get("type"))){
                            Integer peopleCount=Integer.parseInt(String.valueOf(departmentInfo.get("usrCount")));
                            //遍历已有部门结果，统计当前部门人数
                            for (Map<String, Object> resultInfo:resultList) {
                                //获取当前部门的下级部门人数
                                if(String.valueOf(departmentInfo.get("id")).equals(String.valueOf(resultInfo.get("pId")))){
                                    peopleCount=peopleCount+Integer.parseInt(String.valueOf(resultInfo.get("usrCount")));
                                }
                            }
                            //更新部门人数，部门名字
                            departmentInfo.put("usrCount",peopleCount);
                            departmentInfo.put("onlyName", departmentInfo.get("name"));
                            departmentInfo.put("name", String.valueOf(departmentInfo.get("name"))+'(' + peopleCount + ')');
                            resultList.add(departmentInfo);
                        }
                    }
                }
            }
            return resultList;
        }catch (Exception e){
            throw new RuntimeException("获取部门树数据异常"+e.getMessage());
        }
    }


    /**
     * 获取id部门的所有下级部门
     * @param id
     * @param tree
     */
    @Override
    public void findChildDepartment(Integer id,List<Department> tree){
        try {
            List<Department> list = departmentMapper.findChild(id);
            /*获取当前部门list的所有id*/
            List<Integer> idList=new ArrayList<>();
            for (Department department:tree
                 ) {
                idList.add(department.getId());
            }
            if (CollectionUtils.isNotEmpty(list)) {
                for (Department department : list
                        ) {
                    /*判断当前部分是否遍历过，防止死循环*/
                    if (!idList.contains(department.getId())) {
                        Integer childId = department.getId();
                        /*递归查询当前部门的下属所有部门*/
                        findChildDepartment(childId, tree);
                        tree.add(department);
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException("获取部门数据异常"+e.getMessage());
        }
    }


/*    public List<Department> findChildDepartment(Integer id,List tree){
        try {
            List<Department> list = departmentMapper.findChild(id);
            if (CollectionUtils.isNotEmpty(list)) {
                for (Department department : list
                        ) {
                    if (!tree.contains(department)) {
                        Integer childId = department.getId();
                        List childList = findChildDepartment(childId, tree);
                        if (CollectionUtils.isNotEmpty(childList)) {
                            tree.addAll(childList);
                        }
                    }
                    tree.add(department);
                }
            }
            return tree;
        }catch (Exception e){
            throw new RuntimeException("获取部门数据异常"+e.getMessage());
        }
    }*/

    @Override
    public Department findDepById(int id) {
        try {
            return departmentMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            throw new RuntimeException("获取部门数据异常"+e.getMessage());
        }
    }

    /*获取部门成员*/
    @Override
    public List<Map<String,Object>> getMemberByDepartmentId(Integer id){
        try {
            List<Map<String, Object>> userList = new ArrayList();
            if(id != null && id.intValue() != 1) {
                userList = userMapper.getMemberByDepartmentId(id);
            }else{
                id = 1;
                List<Map<String, Object>> userHasDepartmentList = userMapper.getMemberByDepartmentIdHasDepartment();
                List<Map<String, Object>> userNoDepartmentList = userMapper.getMemberByDepartmentIdNoDepartment();
                List<Map<String, Object>> userFromZeroDepartmentList = userMapper.getMemberFromRoot(id);
                for(int i = 0;i<userNoDepartmentList.size();i++){
                    int exitNum = 0;
                    Map<String,Object> noTmp = userNoDepartmentList.get(i);
                    String userIdNoDept = String.valueOf(noTmp.get("id"));
                    for(int m = 0;m<userHasDepartmentList.size();m++){
                        Map<String,Object> hasTmp = userHasDepartmentList.get(m);
                        if(userIdNoDept.equals(String.valueOf(hasTmp.get("id")))){
                            exitNum++;
                        }
                    }
                    if(exitNum == 0){
                        userList.add(noTmp);
                    }
                }
                for(int i = 0;i<userFromZeroDepartmentList.size();i++){
                    Map<String,Object> tmp = userFromZeroDepartmentList.get(i);
                    userList.add(tmp);

                }
            }
            //判断是否为该部门主管
            Department department = departmentMapper.selectByPrimaryKey(id);
            if(department.getChargeId() != null) {
                for (int i = 0; i < userList.size(); i++) {
                    Map<String, Object> tmp = userList.get(i);
                    int leaderId = tmp.get("id") == null ? 0 : Integer.parseInt(tmp.get("id").toString());
                    if (leaderId == department.getChargeId()) {
                        tmp.put("leader", 1);
                    }
                }
            }
            return userList;
        }catch (Exception e){
            throw new RuntimeException("获取部门成员数据异常"+e.getMessage());
        }
    }

    /*根据部门名字获取ID*/
    @Override
    public Integer getIdByName(String name){
        try {
            Integer id = departmentMapper.getIdByName(name);
            return id;
        }catch (Exception e){
            throw new RuntimeException("获取部门成员数据异常"+e.getMessage());
        }
    }


}
