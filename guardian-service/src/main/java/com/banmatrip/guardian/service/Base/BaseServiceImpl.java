package com.banmatrip.guardian.service.Base;

import com.banmatrip.guardian.interfaces.Base.BaseService;
import com.banmatrip.guardian.repository.mapper.common.BaseMapper;
import com.banmatrip.guardian.repository.mapper.membership.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseServiceImpl implements BaseService{
    @Autowired
    BaseMapper baseMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<Map<String, Object>> getDictionaryData(Integer type) {
        return baseMapper.getDictionaryData(type);
    }
    //渠道回显设置
    @Override
    public List<Map<String,Object>> orderPlatformShow(List<Map<String,Object>> orderPlatform,List<Integer> orderPlatformNow){
        List<Map<String,Object>> realDep = new ArrayList<>();
        if (null == orderPlatformNow){
            orderPlatformNow = new ArrayList<>();
        }
        List<Integer> parentId = userMapper.getPlatformParentId();
        List<Map<String,Object>> departmentMapperAll = new ArrayList<>();
        for (int i:parentId){
            Map<String,Object> map = userMapper.getPlatformById(i);
            if (null!=map){
                departmentMapperAll.add(map);
            }
        }
        for (Map<String,Object> dep:departmentMapperAll){
            List<Map<String,Object>> temp =userMapper.getChildChannelDictionaryByParentId((Integer) dep.get("id"));
            List<Map<String,Object>> child = new ArrayList<>();
            Map map = new HashMap();
            map.put("name","   "+dep.get("name"));
            map.put("id", dep.get("id"));
            if (orderPlatformNow.contains(dep.get("id"))){
                map.put("isTrue","true");
            }else
            {map.put("isTrue","false");}
            if (null != temp) {
                for (Map depMap:temp){
                    depMap.put("name","    "+depMap.get("name"));
                    if (orderPlatformNow.contains(depMap.get("id"))){
                        depMap.put("isTrue","true");
                        child.add(depMap);
                    }else {
                        depMap.put("isTrue","false");
                        child.add(depMap);
                    }
                }
                if (orderPlatformNow.contains(dep.get("id"))){
                    dep.put("name",dep.get("name")+"OLD");
                    dep.put("isTrue",true);
                    child.add(dep);
                }else {
                    dep.put("name",dep.get("name")+"OLD");
                    dep.put("isTrue",false);
                    child.add(dep);
                }
                map.put("children", child);
                realDep.add(map);
            }
        }
        List<Map<String,Object>> platWhithoutChil = userMapper.selectPlatformWhithoutChil();
        if (null!=platWhithoutChil){
            for(Map<String,Object> plat:platWhithoutChil){
                Map<String,Object> mapT = new HashMap();
                if (orderPlatformNow.contains(plat.get("id"))){
                    mapT.put("name",plat.get("name"));
                    mapT.put("id",plat.get("id"));
                    mapT.put("isTrue",true);
                }else {
                    mapT.put("name",plat.get("name"));
                    mapT.put("id",plat.get("id"));
                    mapT.put("isTrue",false);
                }
                List<Map<String,Object>> chi = new ArrayList<>();
                if (orderPlatformNow.contains(plat.get("id"))){
                    plat.put("isTrue",true);
                }else {
                    plat.put("isTrue",false);
                }
                chi.add(plat);
                mapT.put("children",chi);
                realDep.add(mapT);
            }
        }
        return realDep;
    }
}
