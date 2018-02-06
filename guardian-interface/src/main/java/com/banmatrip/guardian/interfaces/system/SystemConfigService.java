package com.banmatrip.guardian.interfaces.system;

import com.banmatrip.guardian.domain.SystemConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Miracle Xu
 * @Description: 系统入口配置
 * @create 2017-12-13 13:53
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public interface SystemConfigService {
    /**
     * 全部系统入口配置查询
     * @return List<SystemConfig>
     * */
    List<SystemConfig> systemConfigQuery();
    /**
     * 具体系统入口配置查询
     * @param systemId 系统Id
     * @return SystemConfig
     * */
    SystemConfig selectSystemConfigBySystemId(int systemId);
    /**
     * 系统入口配置修改
     * @param request
     * @return boolean
     * */
    boolean updateSystemConfig(HttpServletRequest request);
    /**
     * 系统入口配置删除
     * @param request
     * @return boolean
     * */
    boolean deleteSystemConfig(HttpServletRequest request);
    /**
     * 系统入口配置新增
     * @param request
     * @return boolean
     * */
    boolean addSystemConfig(HttpServletRequest request);
}
