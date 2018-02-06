package com.banmatrip.guardian.service.system;

import com.banmatrip.guardian.assemble.SystemConfigAssemble;
import com.banmatrip.guardian.domain.SystemConfig;
import com.banmatrip.guardian.interfaces.system.SystemConfigService;
import com.banmatrip.guardian.repository.mapper.system.SystemConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Miracle Xu
 * @Description: 系统入口配置
 * @create 2017-12-13 13:55
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Service
public class SystemConfigServiceImpl implements SystemConfigService{

    @Autowired
    SystemConfigMapper systemConfigMapper;

    /** 全部系统入口配置查询 */
    @Override
    public List<SystemConfig> systemConfigQuery() {
        List<SystemConfig> systemConfig = systemConfigMapper.selectSystemConfig();
        return systemConfig;
    }

    /** 具体系统入口配置查询 */
    @Override
    public SystemConfig selectSystemConfigBySystemId(int systemId) {
        SystemConfig systemConfig = systemConfigMapper.selectSystemConfigById(systemId);
        return systemConfig;
    }

    /** 系统入口配置修改 */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = RuntimeException.class,timeout = 60)
    public boolean updateSystemConfig(HttpServletRequest request) throws RuntimeException{
        //基础信息
        SystemConfig systemConfig = SystemConfigAssemble.assembleSystemConfig(request);
        try {
            systemConfigMapper.updateSystemConfig(systemConfig);
        }catch (Exception e){
            throw new RuntimeException("系统入口配置修改异常"+e.getMessage());
        }
        return true;
    }

    /** 系统入口配置删除 */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = RuntimeException.class,timeout = 60)
    public boolean deleteSystemConfig(HttpServletRequest request) throws RuntimeException{
        //基础信息
        SystemConfig systemConfig = SystemConfigAssemble.assembleSystemConfig(request);
        try {
            SystemConfig systemConfigNow = systemConfigMapper.selectSystemConfigById(systemConfig.getId());
            //获取当前待删除系统入口配置排序，遍历删除后的所有数据，删除节点之后的所有数据向前移一位
            int sort = systemConfigNow.getSort();
            systemConfigMapper.deleteSystemConfig(systemConfig);
            List<SystemConfig> systemConfigList = systemConfigMapper.selectSystemConfig();
            for(int i = 0;i<systemConfigList.size();i++){
                SystemConfig tmp = systemConfigList.get(i);
                if(tmp.getSort() > sort){
                    tmp.setSort(tmp.getSort()-1);
                    systemConfigMapper.updateSystemConfigSort(tmp);
                }
            }
        }catch (Exception e){
            throw new RuntimeException("系统入口配置删除异常"+e.getMessage());
        }
        return true;
    }

    /** 系统入口配置新增 */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = RuntimeException.class,timeout = 60)
    public boolean addSystemConfig(HttpServletRequest request) throws RuntimeException{
        try {
            //基础信息,新增数据默认排序最后
            List<SystemConfig> systemConfigList = systemConfigMapper.selectSystemConfig();
            SystemConfig systemConfig = SystemConfigAssemble.assembleSystemConfigAdd(request,systemConfigList.size());
            systemConfigMapper.addSystemConfig(systemConfig);
        }catch (Exception e){
            throw new RuntimeException("系统入口配置新增异常"+e.getMessage());
        }
        return true;
    }
}
