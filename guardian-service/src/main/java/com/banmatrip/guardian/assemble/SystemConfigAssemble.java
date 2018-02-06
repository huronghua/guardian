package com.banmatrip.guardian.assemble;

import com.banmatrip.guardian.domain.SystemConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Miracle Xu
 * @Description: 系统入口配置参数组装
 * @create 2017-12-13 16:47
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public class SystemConfigAssemble {
    /** 系统入口配置信息 */
    public static SystemConfig assembleSystemConfig(HttpServletRequest request){
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        if("".equals(id)){
            return null;
        }
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String url = request.getParameter("url") == null ? "" : request.getParameter("url");
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setId(Integer.parseInt(id));
        systemConfig.setName(name);
        systemConfig.setUrl(url);
        return systemConfig;
    }

    /** 系统入口配置新增信息 */
    public static SystemConfig assembleSystemConfigAdd(HttpServletRequest request,int sort){
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String url = request.getParameter("url") == null ? "" : request.getParameter("url");
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setName(name);
        systemConfig.setUrl(url);
        systemConfig.setSort(sort+1);
        return systemConfig;
    }
}
