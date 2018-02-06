package com.banmatrip.guardian.controller;

import com.banmatrip.guardian.common.PermissionRender;
import com.banmatrip.guardian.domain.SystemConfig;
import com.banmatrip.guardian.dto.response.base.RestResponse;
import com.banmatrip.guardian.interfaces.system.SystemConfigService;
import com.banmatrip.guardian.interfaces.system.SystemDataMigrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author Miracle Xu
 * @Description: 系统配置管理
 * @create 2017-12-12 16:11
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
@Controller
@RequestMapping("/system")
public class SystemConfigController {

    @Autowired
    SystemConfigService systemConfigService;

    @Autowired
    SystemDataMigrateService systemDataMigrateService;

    @Autowired
    private PermissionRender permissionRender;

    /** 成员信息主页入口 */
    @RequestMapping(value = "/index")
    public String getTree(ModelMap modelMap,Principal principal){
        List<SystemConfig> systemConfigList = systemConfigService.systemConfigQuery();
        modelMap.addAttribute("results",systemConfigList);
        modelMap.addAttribute("info", "");
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "system/index";
    }


    /** 系统配置信息修改 */
    @RequestMapping(value="/edit/{systemId}")
    public String updateSystemConfig(Map<String,Object> model, HttpServletRequest request, @PathVariable int systemId){
        SystemConfig systemConfig = systemConfigService.selectSystemConfigBySystemId(systemId);
        model.put("results",systemConfig);
        model.put("info", "");
        return "system/edit";
    }

    /**更新系统配置信息*/
    @RequestMapping(method = RequestMethod.POST, value="/update")
    public String updateSystemConfig(HttpServletRequest request,ModelMap modelMap,Principal principal) throws Exception{

        boolean result = systemConfigService.updateSystemConfig(request);
        List<SystemConfig> systemConfigList = systemConfigService.systemConfigQuery();
        modelMap.addAttribute("results", systemConfigList);
        if(result) {
            modelMap.addAttribute("info", "系统配置信息更新成功！");
        }else {
            modelMap.addAttribute("info", "系统配置信息更新失败，请稍后重试！");
        }
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "system/index";
    }

    /** 删除系统配置信息 */
    @RequestMapping(method = RequestMethod.POST, value="/delete")
    public String deleteSystemConfig(HttpServletRequest request,ModelMap modelMap,Principal principal){
        boolean result = systemConfigService.deleteSystemConfig(request);
        List<SystemConfig> systemConfigList = systemConfigService.systemConfigQuery();
        modelMap.addAttribute("results",systemConfigList);
        if(result) {
            modelMap.addAttribute("info", "系统配置信息删除成功！");
        }else {
            modelMap.addAttribute("info", "系统配置信息删除失败，请稍后重试！");
        }
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "system/index";
    }

    /** 系统配置信息新增 */
    @RequestMapping(value="/preAdd")
    public String preAddSystemConfig(HttpServletRequest request,ModelMap modelMap){
        modelMap.addAttribute("info","");
        return "system/add";
    }

    /** 新增系统配置信息 */
    @RequestMapping(method = RequestMethod.POST, value="/add")
    public String addSystemConfig(HttpServletRequest request,ModelMap modelMap,Principal principal){
        boolean result = systemConfigService.addSystemConfig(request);
        List<SystemConfig> systemConfigList = systemConfigService.systemConfigQuery();
        modelMap.addAttribute("results",systemConfigList);
        if(result) {
            modelMap.addAttribute("info", "系统配置信息新增成功！");
        }else {
            modelMap.addAttribute("info", "系统配置信息新增失败，请稍后重试！");
        }
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "system/index";
    }

    /**
     * 数据迁移
     * @return
     */
    @PostMapping(value = "/migrateData")
    @ResponseBody
    public RestResponse migrateData(HttpServletRequest request) {
        try {
            systemDataMigrateService.migrateSystemData();
        } catch(Exception e) {
            return new RestResponse(false,"数据迁移失败" + e.getMessage());
        }
        return new RestResponse(true,"数据迁移成功!");
    }
}
