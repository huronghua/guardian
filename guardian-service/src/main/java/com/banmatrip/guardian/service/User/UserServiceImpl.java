package com.banmatrip.guardian.service.User;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.banmatrip.guardian.assemble.UserAssemble;
import com.banmatrip.guardian.assemble.UserUniquenessCheck;
import com.banmatrip.guardian.core.utils.StringUtil;
import com.banmatrip.guardian.domain.*;
import com.banmatrip.guardian.interfaces.Base.BaseService;
import com.banmatrip.guardian.interfaces.Base.MembercacheService;
import com.banmatrip.guardian.interfaces.DepartmentService;
import com.banmatrip.guardian.interfaces.role.RoleService;
import com.banmatrip.guardian.interfaces.user.UserService;
import com.banmatrip.guardian.repository.mapper.common.OrderPlatformMapper;
import com.banmatrip.guardian.repository.mapper.common.ProductOrderTagMapper;
import com.banmatrip.guardian.repository.mapper.common.TagMapper;
import com.banmatrip.guardian.repository.mapper.membership.DepartmentGroupMapper;
import com.banmatrip.guardian.repository.mapper.membership.DepartmentMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserDataRangeMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserMapper;
import com.banmatrip.guardian.repository.mapper.rolepermission.DictionaryMapper;
import com.banmatrip.guardian.repository.mapper.rolepermission.RoleGroupMapper;
import com.banmatrip.guardian.repository.mapper.rolepermission.RoleMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by banma on 2017/9/17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDataRangeMapper userDataRangeMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private DepartmentGroupMapper departmentGroupMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private RoleGroupMapper roleGroupMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    OrderPlatformMapper orderPlatformMapper;

    @Autowired
    ProductOrderTagMapper productOrderTagMapper;

    @Autowired
    BaseService baseService;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleService roleService;

    @Autowired
    MembercacheService membercacheService;

    @Override
    public List<Map<String,Object>> getDestination(int userId) {
        List<Integer> destinantionCol = userDataRangeMapper.selectCheckedData(userId,1);
        List<Map<String,Object>> desAll =  userMapper.getDestination();
        List<Map<String,Object>> result = new ArrayList<>();
        if (null !=destinantionCol){
            for (Map<String,Object> map:desAll){
                if (destinantionCol.contains(map.get("id"))){
                    map.put("name"," "+map.get("name"));
                    map.put("isTrue","true");
                }else {
                    if (destinantionCol.size() == 1&&destinantionCol.get(0)== -999){
                        map.put("isTrue","true");
                    }else {
                        map.put("isTrue","false");
                    }
                    map.put("name"," "+map.get("name"));
                }
                result.add(map);
            }
        }else {
            for (Map<String,Object> map:desAll) {
                map.put("name"," "+map.get("name"));
                map.put("isTrue", "false");
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public List<Map<String,Object>> getProductType(int userId) {

        List<Integer> productCol = userDataRangeMapper.selectCheckedData(userId,3);
        List<Map<String,Object>> desAll =  userMapper.getProductType();
        List<Map<String,Object>> result = new ArrayList<>();
        if (null !=productCol){
            for (Map<String,Object> map:desAll){
                if (productCol.contains(map.get("id"))){
                    map.put("name"," "+map.get("name"));
                    map.put("isTrue","true");
                }else {
                    if (productCol.size() == 1&&productCol.get(0)== -999){
                        map.put("isTrue","true");
                    }else {
                        map.put("isTrue","false");
                    }
                    map.put("name"," "+map.get("name"));
                }
                result.add(map);
            }
        }else {
            for (Map<String,Object> map:desAll) {
                map.put("name"," "+map.get("name"));
                map.put("isTrue", "false");
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public List<Map<String,Object>> getResourceType(int userId) {

        List<Integer> rsourceCol = userDataRangeMapper.selectCheckedData(userId,4);
        List<Map<String,Object>> desAll =  dictionaryMapper.getDictionary(3);
        List<Map<String,Object>> result = new ArrayList<>();
        if (null !=rsourceCol){
            for (Map<String,Object> map:desAll){
                if (rsourceCol.contains(map.get("id"))){
                    map.put("name"," "+map.get("name"));
                    map.put("isTrue","true");
                }else {
                    if (rsourceCol.size() == 1&&rsourceCol.get(0)== -999){
                        map.put("isTrue","true");
                    }else {
                        map.put("isTrue","false");
                    }
                    map.put("name"," "+map.get("name"));
                }
                result.add(map);
            }
        }else {
            for (Map<String,Object> map:desAll) {
                map.put("name"," "+map.get("name"));
                map.put("isTrue", "false");
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getDestination() {
        List<Map<String,Object>> maps = userMapper.getDestination();
        List<Map<String,Object>> result = new ArrayList<>();
        if (null != maps){
            for (Map<String,Object> des:maps){
                des.put("name"," "+des.get("name"));
                result.add(des);
        }}
        return result;
    }

    @Override
    public List<Map<String, Object>> getProductType() {
        List<Map<String,Object>> maps = userMapper.getProductType();
        List<Map<String,Object>> result = new ArrayList<>();
        if (null != maps){
            for (Map<String,Object> des:maps){
                des.put("name"," "+des.get("name"));
                result.add(des);
            }}
        return result;
    }


    @Override
    public List<Map<String, Object>> getPlatform() {
        List<Integer> parentId = userMapper.getPlatformParentId();
        List<Map<String,Object>> departmentMapperAll = new ArrayList<>();
        for (int i:parentId){
            Map<String,Object> map = userMapper.getPlatformById(i);
            if (null!=map){
                departmentMapperAll.add(map);
            }
        }
        List<Map<String,Object>> result = new ArrayList<>();
        for (Map<String,Object> temp:departmentMapperAll){
            List<Map<String,Object>> child = userMapper.getChildPlatform((Integer) (temp.get("id")));
            Map map = new HashMap();
            map.put("id",temp.get("id"));
            map.put("name"," "+temp.get("name"));
            if (null != child){
                List<Map<String,Object>> childRes = new ArrayList<>();
                for(Map<String,Object> ch:child){
                    ch.put("name"," "+ch.get("name"));
                    childRes.add(ch);
                }
                temp.put("name",temp.get("name")+"OLD");
                childRes.add(temp);
                map.put("children",childRes);
            }
            result.add(map);
        }
        List<Map<String,Object>> platWhithoutChil = userMapper.selectPlatformWhithoutChil();
        if (null!=platWhithoutChil){
            for(Map<String,Object> plat:platWhithoutChil){
                Map<String,Object> map = new HashMap();
                map.put("name",plat.get("name"));
                map.put("id",plat.get("id"));
                List<Map<String,Object>> chi = new ArrayList<>();
                chi.add(plat);
                map.put("children",chi);
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getResourceType() {
        List<Map<String,Object>> maps = dictionaryMapper.getDictionary(3);;
        List<Map<String,Object>> result = new ArrayList<>();
        if (null != maps){
            for (Map<String,Object> des:maps){
                des.put("name"," "+des.get("name"));
                result.add(des);
            }}
        return result;
    }

    @Transactional(propagation=Propagation.REQUIRED,isolation = Isolation.READ_UNCOMMITTED,rollbackFor = RuntimeException.class,timeout = 120)
    @Override
    public synchronized boolean addUser(Map user, Map conf) throws RuntimeException{
        List<User> userList = userMapper.selectAllUser();//获取所有用户
        try {
            UserUniquenessCheck.userAssemble(userList,user);//用户唯一性校验
            int departmentId = (null==userMapper.selectMaxDepId()?1:userMapper.selectMaxDepId()+1);
            user.put("departmentGroupId",departmentId);
            userMapper.insertUserByNoId(user);
            int userId = (Integer) user.get("id");
            conf.put("id",userId);

            String roleListString = user.get("roleList") == null ? "" : (String) user.get("roleList");
            if(!("".equals(roleListString))) {
                List<Integer> roleList = StringUtil.addSingleQuoteToList(roleListString);
                Map<String, Object> roleParam = new HashMap();
                roleParam.put("list", roleList);
                roleParam.put("updateId", user.get("updateId"));
                roleParam.put("id", userId);
                //更新角色信息
                List<Integer> roleParamList = roleMapper.findAllForUser();
                Map<String,Object> roleParamMap = new HashMap();
                roleParamMap.put("id",userId);
                roleParamMap.put("roleList",roleParamList);
                userDataRangeMapper.deleteRoleByUserId(roleParamMap);
                userDataRangeMapper.insertRoleByEditInfo(roleParam);
            }

        //同步更新adminAccount表相关数据
        userMapper.insertOneAdminAcountByUserList(user);//插入adminAcount表
        user.put("userId",userId);
        userMapper.insertSSOContrast(user);//更新sso_contrast_id表

        /*根据userId查询出orange系统的adminId*/
        Integer adminId = roleService.selectAdminIdByUserId(userId);
        List<Integer> adminIdList = new ArrayList<>();
        adminIdList.add(adminId);
        /*插入orange系统的memberchache中通知权限变更*/
        membercacheService.notifyPermissionChangeByAdminId(adminIdList);
//        初始化orange权限,已废弃
//        String adminId = user.get("id") == null ?  "" : user.get("id").toString();//admin_account的用户id
//        String ethnic = user.get("ethnic") == null ?  "" : user.get("ethnic").toString();//族群
//        String roleType = user.get("roleType") == null ?  "" : user.get("roleType").toString();//序列
//        initAuth(adminId,ethnic,roleType);

            List desTemp = (null==conf.get("destination")|| StringUtils.isBlank(conf.get("destination").toString()))?null:stringCastList((String) conf.get("destination"));
            List plaTemp = (null==conf.get("platform")|| StringUtils.isBlank(conf.get("platform").toString()))?null:stringCastList((String) conf.get("platform"));
            List proTemp = (null==conf.get("productType")|| StringUtils.isBlank(conf.get("productType").toString()))?null:stringCastList((String) conf.get("productType"));
            List resTemp = (null==conf.get("resourceType")|| StringUtils.isBlank(conf.get("resourceType").toString()))?null:stringCastList((String) conf.get("resourceType"));
            conf.put("data",desTemp);
            conf.put("rangeType",1);
            if (desTemp!=null){
                userDataRangeMapper.insertDataRange(conf);
            }
            conf.remove("data");
            conf.put("rangeType",2);
            conf.put("data",plaTemp);
            if (plaTemp!=null){
                userDataRangeMapper.insertDataRange(conf);
            }
            conf.remove("data");
            conf.put("rangeType",3);
            conf.put("data",proTemp);
            if (proTemp!=null){
                userDataRangeMapper.insertDataRange(conf);
            }
            conf.remove("data");
            conf.put("rangeType",4);
            conf.put("data",resTemp);
            if (resTemp!=null){
                userDataRangeMapper.insertDataRange(conf);
            }
            List<Integer> department = stringCastList((String) user.get("department"));
            departmentGroupMapper.insertUserDepartment(department,departmentId);
            return true;
        }catch (Exception e){
            throw new RuntimeException("新增用户异常:"+e.getMessage());
        }
    }

    //批量新增用户
    @Override
    @Transactional(propagation=Propagation.REQUIRED,isolation = Isolation.READ_UNCOMMITTED,rollbackFor = RuntimeException.class,timeout = 120)
    public boolean batchAddUser(List<List<Map>> list,int id) throws RuntimeException{
        try {
            List<User> userList = userMapper.selectAllUser();//获取所有用户
            List<Map<String, Object>> userAccountList = userMapper.selectAllUserAccount();//获取所有用户
            if(CollectionUtils.isNotEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    if (CollectionUtils.isNotEmpty(list.get(i))) {
                        Map<String,Object> user = list.get(i).get(0);
                        UserUniquenessCheck.userAssemble(userList, user);//用户唯一性校验
                        UserUniquenessCheck.userAccountAssemble(userAccountList, user);//adminAccount用户唯一性校验
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException("批量导入用户数据异常"+e.getMessage());
        }
        //批量存入用户基本信息
        List<Map<String,Object>> userList = UserAssemble.userBaseInfoListAssemble(list,id);
        //userMapper.insertByUserList(userList);//批量插入用户基本信息,批量返回主键
        //userMapper.insertAdminAcountByUserList(userList);//批量插入adminAcount表
        for(int i = 0;i<userList.size();i++){
            Map<String,Object> userTmp = userList.get(i);
            Map<String,Object> adminAccountTtmp = userTmp;
            userMapper.insertOneByUserList(userTmp);//插入用户基本信息
            adminAccountTtmp.put("userId",userTmp.get("id"));
            userMapper.insertOneAdminAcountByUserList(adminAccountTtmp);//插入adminAcount表
            userMapper.insertSSOContrast(adminAccountTtmp);//更新sso_contrast_id表
            if(adminAccountTtmp.get("role") != null) {
                List<String> roleTransList = (List<String>) adminAccountTtmp.get("role");
                for(int n = 0;n<roleTransList.size();n++) {
                    adminAccountTtmp.put("role",roleTransList.get(n));
                    roleMapper.insertOneUserRoleByUserList(adminAccountTtmp);//插入sso_user_role表
                }
            }
            //初始化orange权限
            //String adminId = adminAccountTtmp.get("id") == null ?  "" : adminAccountTtmp.get("id").toString();//admin_account的用户id
            //String ethnic = adminAccountTtmp.get("ethnic") == null ?  "" : adminAccountTtmp.get("ethnic").toString();//族群
            //String roleType = adminAccountTtmp.get("roleType") == null ?  "" : adminAccountTtmp.get("roleType").toString();//序列
            //initAuth(adminId,ethnic,roleType);
            userTmp.put("id",adminAccountTtmp.get("userId"));//保存sso_user用户id
        }
        userMapper.updateDepartmentByUserList(userList);//批量更新部门组信息
        departmentGroupMapper.insertDepartmentByDepartmentList(userList);//批量更新部门信息
        List<Map<String,Object>> dataList;
        //批量存入用户的目的地信息
        dataList = UserAssemble.dataListAssemble(userList,list,"destination",1);
        if(CollectionUtils.isNotEmpty(dataList)){
            userDataRangeMapper.insertDataRangeByDataList(dataList);
        }
        //批量存入用户的渠道信息
        dataList = UserAssemble.dataListAssemble(userList,list,"platform",2);
        if(CollectionUtils.isNotEmpty(dataList)){
            userDataRangeMapper.insertDataRangeByDataList(dataList);
        }
        //批量存入用户的产品类型信息
        dataList = UserAssemble.dataListAssemble(userList,list,"productType",3);
        if(CollectionUtils.isNotEmpty(dataList)){
            userDataRangeMapper.insertDataRangeByDataList(dataList);
        }
        //批量存入用户的资源类型信息
        dataList = UserAssemble.dataListAssemble(userList,list,"resourceType",4);
        if(CollectionUtils.isNotEmpty(dataList)){
            userDataRangeMapper.insertDataRangeByDataList(dataList);
        }
        return true;
    }

    @Override
    public List<String> getUserNameList() {
        return userMapper.selectUserNameList();
    }

    @Override
    public List<Map<String,Object>> getDepListWithEcho(int id) {
        List<Integer> col = userMapper.selectDepById(id);
        List<Map<String,Object>> list=departmentService.findAll();
        List<Map<String,Object>> resultlist=new ArrayList<Map<String,Object>>();
            if (null != col){
                for (Map<String,Object> map:list
                        ) {
                    if (col.contains(map.get("id"))) {
                        map.put("checked", "true");
                    }
                    JSONObject item= JSONObject.parseObject(JSON.toJSONString(map));
                    resultlist.add(item);
                }
            }
        return resultlist;
    }

    @Override
    public List<Map<String ,Object>> getDepListWithEchoDept(int deptId) {
        List<Integer> col = new ArrayList();
        col.add(deptId);
        List<Map<String,Object>> list=departmentService.findAll();
        List<Map<String,Object>> resultlist=new ArrayList<Map<String,Object>>();
        if (null != col){
            for (Map<String,Object> map:list
                    ) {
                if (col.contains(map.get("id"))) {
                    map.put("checked", "true");
                }
                JSONObject item= JSONObject.parseObject(JSON.toJSONString(map));
                resultlist.add(item);
            }
        }
        return resultlist;

    }

    @Override
    public List<Map<String, Object>> getPosition(int id) {
        List<Map<String,Object>> roleTypeDic = dictionaryMapper.getDictionary(1);
        String col = userMapper.selectPositionIdList(id);
        List<Map<String,Object>> result = new ArrayList<>();
        for (Map map:roleTypeDic){
            if (null!=col){
                if (col.equals(map.get("code"))){
                    map.put("name"," "+map.get("name"));
                    map.put("isTrue","true");
                }else {
                    map.put("name"," "+map.get("name"));
                    map.put("isTrue","false");
                }
            }else {
                map.put("name"," "+map.get("name"));
                map.put("isTrue","false");
            }
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getRoleType(int id) {
        List<Map<String,Object>> roleTypeDic = dictionaryMapper.getDictionary(2);
        String col = userMapper.selectRoleTypeIdList(id);
        List<String> roleTypeCol = new ArrayList<>();
        if (null != col)
        roleTypeCol = stringCastStingList(col);
        List<Map<String,Object>> result = new ArrayList<>();
        for (Map map:roleTypeDic){
            if (null!=col){
            if (roleTypeCol.contains(map.get("code"))){
                map.put("name"," "+map.get("name"));
                map.put("isTrue","true");
            }else {
                map.put("name"," "+map.get("name"));
                map.put("isTrue","false");
            }
            }else {
                map.put("name"," "+map.get("name"));
                map.put("isTrue","false");
            }
            result.add(map);
        }
        return result;
    }


    @Override
    public List<Map<String, Object>> getEthnic(int id) {
        List<Map<String,Object>> roleTypeDic = dictionaryMapper.getDictionary(7);
        String col = userMapper.selectEthnicIdList(id);
        List<String> roleTypeCol = new ArrayList<>();
        if (null != col)
        roleTypeCol = stringCastStingList(col);
        List<Map<String,Object>> result = new ArrayList<>();
        for (Map map:roleTypeDic){
            if (null!=col){
                if (roleTypeCol.contains(map.get("code"))){
                    map.put("name"," "+map.get("name"));
                    map.put("isTrue","true");
                }else {
                    map.put("name"," "+map.get("name"));
                    map.put("isTrue","false");
                }
            }else {
                map.put("name"," "+map.get("name"));
                map.put("isTrue","false");
            }
            result.add(map);
        }
        return result;
    }
    @Override
    public List<Object> getDepListWithEchoParentId(int id) {
        List<Map<String,Object>> dep=departmentMapper.selectDepTree();
        List<Integer> col = departmentMapper.selectParentIdById(id);
        List<Object> result = new ArrayList<>();
        for (Map map:dep){
            if (null != col){
                if (col.contains(map.get("id"))){
                    map.put("checked","true");
                }
            }
            JSONObject item= JSONObject.parseObject(JSON.toJSONString(map));
            result.add(item);
        }
        return result;
    }

    @Override
    public List<Map<String,Object>> getDepListWithEchoNowId(int departmentId) {
        List<Integer> col = new ArrayList();
        col.add(departmentId);//当前部门
        List<Map<String,Object>> list=departmentMapper.findAll();//所有部门
        List<Map<String,Object>> resultlist=new ArrayList<Map<String,Object>>();
        for (Map<String,Object> map:list
                ) {
            /*获取当前部门的下属所有部门*/
            List<Department> departmentList=findChildDepartment((Integer) map.get("id"));
            /*获取当前部门信息*/
            Department department=departmentMapper.selectByPrimaryKey((Integer) map.get("id"));
            /*当前部门及其下属部门*/
            departmentList.add(department);
            /*获取本部门及其下属部门的员工人数*/
            Integer peopleCount = userMapper.getPeopleCount(departmentList);
            if (null != col){
                if (col.contains(map.get("id"))){
                    map.put("checked","true");
                }
            }
            map.put("onlyName",map.get("name"));
            map.put("name", (String) map.get("name") + '('+peopleCount+')');
            JSONObject item= JSONObject.parseObject(JSON.toJSONString(map));
            resultlist.add(item);
        }
        return resultlist;
    }


    @Override
    public List<Map<String,Object>> getPlatform(int userId) {
        List<Integer> parentId = userMapper.getPlatformParentId();
        List<Map<String,Object>> departmentMapperAll = new ArrayList<>();
        for (int i:parentId){
            Map<String,Object> map = userMapper.getPlatformById(i);
            if (null!=map){
                departmentMapperAll.add(map);
            }
        }
        List<Map<String,Object>> realDep = new ArrayList<>();
        List<Integer> depTrue = userDataRangeMapper.selectCheckedData(userId,2);
        for (Map<String,Object> dep:departmentMapperAll) {
            List<Map<String,Object>> temp =userMapper.getChildPlatform((Integer) dep.get("id"));
            List<Map<String,Object>> child = new ArrayList<>();
            if (null == depTrue){
                depTrue = new ArrayList<>();
            }
            Map map = new HashMap();
            map.put("name", " "+dep.get("name"));
            map.put("id", dep.get("id"));
            if (depTrue.contains(dep.get("id"))){
                map.put("name", " "+dep.get("name"));
                map.put("isTrue","true");
            }else
            {
                if (depTrue.size()==1&&depTrue.get(0)==-999){
                    map.put("isTrue","true");
                }else {
                    map.put("isTrue","false");
                }
                map.put("name", " "+dep.get("name"));
            }
            if (null != temp) {
                for (Map depMap:temp){
                    if (depTrue.contains(depMap.get("id"))){
                        map.put("name", " "+dep.get("name"));
                        depMap.put("name"," "+depMap.get("name"));
                        depMap.put("isTrue","true");
                        child.add(depMap);
                    }else {
                        map.put("name", " "+dep.get("name"));
                        depMap.put("name"," "+depMap.get("name"));
                        if (depTrue.size()==1&&depTrue.get(0)==-999){
                            depMap.put("isTrue","true");
                        }else {
                            depMap.put("isTrue","false");
                        }
                        child.add(depMap);
                    }
                }
                if (depTrue.contains(dep.get("id"))){
                    dep.put("name",dep.get("name")+"OLD");
                    dep.put("isTrue","true");
                    child.add(dep);
                }else {
                    dep.put("name",dep.get("name")+"OLD");
                    if (depTrue.size()==1&&depTrue.get(0)==-999){
                        dep.put("isTrue","true");
                    }else {
                        dep.put("isTrue","false");
                    }
                    child.add(dep);
                }
                map.put("children", child);
                realDep.add(map);
            }
        }
        List<Map<String,Object>> platWhithoutChil = userMapper.selectPlatformWhithoutChil();
        if (null!=platWhithoutChil){
            for(Map<String,Object> plat:platWhithoutChil){
                List<Map<String,Object>> child = new ArrayList<>();
                Map<String,Object> map = new HashMap();
                if (depTrue.contains(plat.get("id"))){
                    map.put("name",plat.get("name"));
                    map.put("id",plat.get("id"));
                    map.put("isTrue",true);
                    plat.put("name",plat.get("name"));
                    plat.put("isTrue",true);
                    child.add(plat);
                }else {
                    map.put("name",plat.get("name"));
                    map.put("id",plat.get("id"));
                    if (depTrue.size()==1&&depTrue.get(0)==-999){
                        map.put("isTrue","true");
                        plat.put("isTrue","true");
                    }else {
                        map.put("isTrue","false");
                        plat.put("isTrue","false");
                    }
                    plat.put("name",plat.get("name"));
                    child.add(plat);
                }
                map.put("children", child);
                realDep.add(map);
            }
        }
        return realDep;

    }

    @Override
    public void updateUser(Map map) {
        userMapper.updateUser(map);
    }

    @Override
    public List<Integer> getDepById(int id) {
        return userMapper.selectDepById(id);
    }

    @Override
    public List<Map<String,Object>> getDataRange(int id) {
        return userMapper.selectDataRangeById(id);
    }

    @Override
    public void deleteUserAndDataRange(int id) {
//        userMapper.deleteByUserId(id);
        userDataRangeMapper.deleteByUserId(id);

    }

    @Override
    public int insertUserAndDataRange(Map user, Map conf) {
        List desTemp = (null==conf.get("destination")||StringUtils.isBlank(conf.get("destination").toString()))?null:stringCastList((String) conf.get("destination"));
        List plaTemp = (null==conf.get("platform")|| StringUtils.isBlank(conf.get("platform").toString()))?null:stringCastList((String) conf.get("platform"));
        List proTemp = (null==conf.get("productType")||StringUtils.isBlank(conf.get("productType").toString()))?null:stringCastList((String) conf.get("productType"));
        List resTemp = (null==conf.get("resourceType")||StringUtils.isBlank(conf.get("resourceType").toString()))?null:stringCastList((String) conf.get("resourceType"));
        conf.put("data",desTemp);
        conf.put("rangeType",1);
        if (null!=desTemp){
            userDataRangeMapper.insertDataRange(conf);
        }
        conf.remove("data");
        conf.put("rangeType",2);
        conf.put("data",plaTemp);
        if (null != plaTemp){
            userDataRangeMapper.insertDataRange(conf);
        }
        conf.remove("data");
        conf.put("rangeType",3);
        conf.put("data",proTemp);
        if (null != proTemp){
            userDataRangeMapper.insertDataRange(conf);
        }
        conf.remove("data");
        conf.put("rangeType",4);
        conf.put("data",resTemp);
        if (null != resTemp){
            userDataRangeMapper.insertDataRange(conf);
        }
        List<Integer> department = stringCastList((String) user.get("department"));
        user.put("departmentGroupId",user.get("departmentGroup"));
        userMapper.updateUser(user);
        //同步更新adminAccount表相关数据
        List<Integer> adminAccountId = userMapper.selectAdminAccountById(user);
        if(CollectionUtils.isNotEmpty(adminAccountId)) {
            user.put("adminAccountId", adminAccountId.get(0));
            userMapper.updateOneAdminAcountByUserList(user);
        }
        departmentGroupMapper.insertUserDepartment(department,Integer.parseInt((String) user.get("departmentGroupId")) );
        return department.get(0);
    }

    @Transactional(propagation=Propagation.REQUIRED,isolation = Isolation.READ_UNCOMMITTED,rollbackFor = RuntimeException.class,timeout = 120)
    @Override
    public int editUser(Map param, Map config, int id) throws RuntimeException{
        try {
            deleteUserAndDataRange(id);
            departmentGroupMapper.deleteDepartment(Integer.parseInt((String) param.get("departmentGroup")));
            int deptId = insertUserAndDataRange(param, config);
            String roleListString = param.get("roleList") == null ? "" : (String) param.get("roleList");
            //更新角色信息
            List<Integer> roleParamList = roleMapper.findAllForUser();
            Map<String, Object> roleParamMap = new HashMap();
            roleParamMap.put("id", id);
            roleParamMap.put("roleList", roleParamList);
            userDataRangeMapper.deleteRoleByUserId(roleParamMap);
            if (!("".equals(roleListString))) {
                List<Integer> roleList = StringUtil.addSingleQuoteToList(roleListString);
                Map<String, Object> roleParam = new HashMap();
                roleParam.put("list", roleList);
                roleParam.put("updateId", param.get("updateId"));
                roleParam.put("id", id);
                userDataRangeMapper.insertRoleByEditInfo(roleParam);
            }
            return deptId;
        }catch (Exception e){
            throw new RuntimeException("修改用户信息异常:"+e.getMessage());
        }
    }

    @Override
    public List<Map<String,Object>> getDictionary(int code) {
        List<Map<String,Object>> maps = dictionaryMapper.getDictionary(code);
        List<Map<String,Object>> result = new ArrayList<>();
        if (null != maps){
            for (Map<String,Object> des:maps){
                des.put("name"," "+des.get("name"));
                result.add(des);
            }}
        return result;
    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    private List stringCastList(String string){
        List result = new ArrayList();
        String[] temp = string.split(",");
        for (int i = 0;i<temp.length;i++){
            if(temp[i] == null || temp[i] == ""){
                continue;
            }
            String st = temp[i];
            result.add(Integer.parseInt(st));
        }
        return result;
    }
    private List stringCastStingList(String string){
        List result = new ArrayList();
        String[] temp = string.split(",");
        for (int i = 0;i<temp.length;i++){
            String st = temp[i];
            result.add(st);
        }
        return result;
    }
    /*获取id部门的所有下级部门*/
    public List<Department> findChildDepartment(Integer id){
        List tree=new ArrayList();
        List<Department> list=departmentMapper.findChild(id);
        if (CollectionUtils.isNotEmpty(list)) {
            tree.addAll(list);
            for (Department department : list
                    ) {
                Integer childId = department.getId();
                List childList = findChildDepartment(childId);
                if (CollectionUtils.isNotEmpty(childList)) {
                    tree.addAll(childList);
                }
            }
        }
        return tree;
    }

    /*根据姓名查询用户*/
    @Override
    public List<Map<String,Object>> queryUserByName(String name){
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("name",name);
        List<Map<String,Object>> userList=userMapper.queryUserByName(param);
        return userList;
    }

    @Override
    public List<String> getUserEmployeeIdList() {
        return userMapper.selectUserEmployeeId();
    }

    @Override
    public List<String> getUserEmaiList() {
        return userMapper.selectUserEmail();
    }

    @Override
    public List<String> getUserOrangeAccountList() {
        return userMapper.selectUserOrangeAcc();
    }

    @Override
    public List<String> getUserEmployeeIdList(int id) {
        return userMapper.selectUserEmployeeIdEx(id);
    }

    @Override
    public List<String> getUserEmaiList(int id) {
        return userMapper.selectUserEmailEx(id);
    }

    @Override
    public List<String> getUserOrangeAccountList(int id) {
        return userMapper.selectUserOrangeAccEx(id);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED,isolation = Isolation.READ_UNCOMMITTED,rollbackFor = RuntimeException.class,timeout = 120)
    public void deleteUserById(int id) {
        userMapper.deleteUserById(id);
    }

    /*用户初始化orange权限*/
    @Override
    public void initAuth(String adminId, String ethnic, String roleType) {
        Map<String,Object> param = new HashMap();
        param.put("adminId",adminId);
        List<Map<String,Object>> authList = userMapper.selectAllAuth();
        String authString;
        if("1".equals(ethnic) && "1".equals(roleType)){
            authString = "旅行管家";
        }else if("2".equals(ethnic) && "2".equals(roleType)){
            authString = "产品";
        }else if("3".equals(ethnic) && ("3".equals(roleType) || "6".equals(roleType) || "7".equals(roleType) || "8".equals(roleType))){
            authString = "操作";
        }else if("5".equals(ethnic) && "17".equals(roleType)){
            authString = "销售";
        }else if("6".equals(ethnic) && "9".equals(roleType)){
            authString = "运营";
        }else if("7".equals(ethnic) && ("10".equals(roleType) || "11".equals(roleType))){
            authString = "人事";
        }else if("8".equals(ethnic) && ("12".equals(roleType) || "13".equals(roleType))){
            authString = "财务";
        }else if("9".equals(ethnic) && ("14".equals(roleType) || "16".equals(roleType))){
            authString = "推广";
        }else if("10".equals(ethnic) && ("20".equals(roleType) || "21".equals(roleType) || "22".equals(roleType) || "23".equals(roleType) || "24".equals(roleType))){
            authString = "研发测试,测试";
        }else{
            return;
        }
        List<String> authTmpList = StringUtil.addSingleQuoteToList(authString);//分割权限名称
        for(int m = 0;m<authTmpList.size();m++) {       //遍历权限
            String authName = authTmpList.get(m);
            for (int i = 0; i < authList.size(); i++) {     //判断数据库字典是否存在该权限
                Map<String, Object> tmp = authList.get(i);
                if (authName.equals(tmp.get("name"))) {
                    param.put("privilege", tmp.get("id"));
                    userMapper.insertInitAuth(param);
                    break;
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> getMemberDisableList() {
        List<Map<String,Object>> memberDisableList = userMapper.selectAllDisableMember();
        return memberDisableList;
    }

    @Override
    public Map<String, Object> batchEdit(String result, int id) {

        Map<String,Object> model = new HashMap();
        List<Role> roleList = roleMapper.findAll();//角色
        List<Tag> tag = tagMapper.selectAllTag();//目的地
        List<Map<String,Object>> orderPlatform = this.getPlatform();//渠道
        List<Integer> orderPlatformNow = new ArrayList();
        List<Map<String,Object>> realDep = baseService.orderPlatformShow(orderPlatform,orderPlatformNow);
        List<ProductOrderTag> productOrderTag = productOrderTagMapper.selectAllProductOrderTag();//产品类型
        List<Map<String,Object>> resourceType = dictionaryMapper.selectAllResourceType();//资源类型

        //获取当前选中人员所属角色和所属部门
        List<Integer> memberIdList = StringUtil.addSingleQuoteToList(result);
        List<Map<String,Object>> memberRoleList = roleMapper.selectMemberRoleById(memberIdList);
        List<Map<String,Object>> departmentNowList = departmentMapper.selectDepartmentById(memberIdList);
        List<Integer> roleNowList = new ArrayList();
        model.put("roleListNow",roleNowList);
        //是否相同角色
        if(memberRoleList.size() != 0) {
            if (memberRoleList.size() >= 2) {
                Map firstMemberRoleMapTmp = memberRoleList.get(0);
                String firstMemberRoleTmp = firstMemberRoleMapTmp.get("roleId") == null ? "" : (String) firstMemberRoleMapTmp.get("roleId");
                for (int i = 1; i < memberRoleList.size(); i++) {
                    Map otherMemberRoleTmp = memberRoleList.get(i);
                    if (!firstMemberRoleTmp.equals(otherMemberRoleTmp.get("roleId"))) {
                        model.put("diffRole", 1);
                        break;
                    }
                    if (i == (memberRoleList.size() - 1)) {
                        roleNowList = StringUtil.addSingleQuoteToListInteger(firstMemberRoleTmp);
                        model.put("roleListNow", roleNowList);
                    }
                }
            } else {
                model.put("diffRole", 1);
            }
        }
        //是否相同部门
        List<Map<String,Object>> departmentList = getDepListWithEcho(Integer.parseInt(String.valueOf(memberIdList.get(0))));
        if(departmentNowList.size() != 0) {
            if (departmentNowList.size() >= 2) {
                Map firstDepartmentMapTmp = departmentNowList.get(0);
                String firstDepartmentTmp = firstDepartmentMapTmp.get("departmentId") == null ? "" : (String) firstDepartmentMapTmp.get("departmentId");
                for (int i = 1; i < departmentNowList.size(); i++) {
                    Map otherDepartmentTmp = departmentNowList.get(i);
                    if (!firstDepartmentTmp.equals(otherDepartmentTmp.get("departmentId"))) {
                        model.put("diffDepartment", 1);
                        break;
                    }
                }
            }
        }
        model.put("tag",tag);
        model.put("platform",realDep);
        model.put("productOrderTag",productOrderTag);
        model.put("resourceType",resourceType);
        model.put("roleList",roleList);
        model.put("depWithEcho",departmentList);
        model.put("memberIdList",memberIdList);
        return model;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = RuntimeException.class,timeout = 60)
    public boolean batchMemberEdit(HttpServletRequest request, Integer id) throws RuntimeException{
        //参数组装
        List<Map<String,Object>> memberInfoList = UserAssemble.batchMemberEditListAssemble(request,id);
        List<Map<String,Object>> dataList;
        String diffDepartment = request.getParameter("diffDepartment");
        String diffRole = request.getParameter("diffRole");
        try {
            //更新角色信息
            dataList = UserAssemble.dataListBatchRoleAndDeptAssemble(memberInfoList, "role");
            List<Integer> roleList = roleMapper.findAllForUser();
            Map<String,Object> roleParam = new HashMap();
            roleParam.put("list",memberInfoList);
            roleParam.put("roleList",roleList);
            if(!("1".equals(diffRole))) {
                userDataRangeMapper.deleteDataRangeRoleByList(roleParam);
                if (CollectionUtils.isNotEmpty(dataList)) {
                    userDataRangeMapper.updateRoleRangeByList(dataList);
                }
            }
            //更新部门信息
            dataList = UserAssemble.dataListBatchRoleAndDeptAssemble(memberInfoList, "department");
            if(!("1".equals(diffDepartment))) {
                if (CollectionUtils.isNotEmpty(dataList)) {
                    departmentGroupMapper.deleteDepartmentByBatchList(dataList);
                    departmentGroupMapper.insertDepartmentByBatchList(dataList);
                }
            }
            //先删除已有用户数据
            userDataRangeMapper.deleteDataRangeByList(memberInfoList);
            //批量存入用户的目的地信息
            dataList = UserAssemble.dataListBatchAssemble(memberInfoList, "tag", 1);
            if (CollectionUtils.isNotEmpty(dataList)) {
                userDataRangeMapper.insertDataRangeByDataList(dataList);
            }
            //批量存入用户的渠道信息
            dataList = UserAssemble.dataListBatchAssemble(memberInfoList, "orderPlatform", 2);
            if (CollectionUtils.isNotEmpty(dataList)) {
                userDataRangeMapper.insertDataRangeByDataList(dataList);
            }
            //批量存入用户的产品类型信息
            dataList = UserAssemble.dataListBatchAssemble(memberInfoList, "productType", 3);
            if (CollectionUtils.isNotEmpty(dataList)) {
                userDataRangeMapper.insertDataRangeByDataList(dataList);
            }
            //批量存入用户的资源类型信息
            dataList = UserAssemble.dataListBatchAssemble(memberInfoList, "resourceType", 4);
            if (CollectionUtils.isNotEmpty(dataList)) {
                userDataRangeMapper.insertDataRangeByDataList(dataList);
            }
        }catch (Exception e){
            throw new RuntimeException("批量更新失败"+e.getMessage());
        }
        return true;
    }

    @Override
    public Map<String, Object> checkConf(Map<String, Object> conf) {
        List desTemp = (null==conf.get("destination")||StringUtils.isBlank(conf.get("destination").toString()))?null:stringCastList((String) conf.get("destination"));
        List plaTemp = (null==conf.get("platform")|| StringUtils.isBlank(conf.get("platform").toString()))?null:stringCastList((String) conf.get("platform"));
        List proTemp = (null==conf.get("productType")||StringUtils.isBlank(conf.get("productType").toString()))?null:stringCastList((String) conf.get("productType"));
        List resTemp = (null==conf.get("resourceType")||StringUtils.isBlank(conf.get("resourceType").toString()))?null:stringCastList((String) conf.get("resourceType"));
        if (desTemp != null&&desTemp.size() == dictionaryMapper.selectDesCount()){
            conf.put("destination","-999");

        }
        if (plaTemp != null&&plaTemp.size() == dictionaryMapper.selectPlatCount()){
            conf.put("platform","-999");

        }
        if (proTemp != null&&proTemp.size() == dictionaryMapper.selectProCount()){
            conf.put("productType","-999");

        }
        if (resTemp != null&&resTemp.size() == dictionaryMapper.selectResCount()){
            conf.put("resourceType","-999");

        }
        return conf;
    }

    @Override
    public void passwordReset(Integer id) {
        if(id != null){
            Map<String,Object> param = new HashMap();
            param.put("id",id);
            param.put("password","123456");
            userMapper.passwordRest(param);
        }
    }
}
