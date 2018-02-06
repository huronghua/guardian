package com.banmatrip.guardian.repository.mapper.system;

import com.banmatrip.guardian.domain.SystemConfig;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Miracle Xu
 * @Description: 系统入口配置mapper
 * @create 2017-12-13 14:03
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public interface SystemConfigMapper {
    List<SystemConfig> selectSystemConfig();
    SystemConfig selectSystemConfigById(int systemId);
    void updateSystemConfig(@Param("systemConfig")SystemConfig systemConfig);
    void deleteSystemConfig(@Param("systemConfig")SystemConfig systemConfig);
    void addSystemConfig(@Param("systemConfig")SystemConfig systemConfig);
    void updateSystemConfigSort(SystemConfig systemConfig);
}
