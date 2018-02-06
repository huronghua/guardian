package com.banmatrip.guardian.controller;

import com.banmatrip.guardian.common.PermissionRender;
import com.banmatrip.guardian.domain.Function;
import com.banmatrip.guardian.interfaces.function.FunctionService;
import com.banmatrip.guardian.interfaces.permission.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-07 13:23
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/

@Controller
@RequestMapping(value = "/function")
public class FunctionController {

    @Autowired
    FunctionService functionService;

    @Autowired
    private PermissionRender permissionRender;

    @RequestMapping(value = "/index")
    public String functionIndex(ModelMap modelMap, Principal principal){
        List<Map<String,Object>> functionMapList = functionService.getAllFunction();
        List<Map<String,Object>> functionTypeMapList = functionService.getFunctionTypeList();
        for(Map<String,Object> functionMap : functionMapList){
            for(Map<String,Object> functionTypeMap : functionTypeMapList) {
                if(String.valueOf(functionMap.get("type")).equals(String.valueOf(functionTypeMap.get("code")))){
                    functionMap.put("typeName",String.valueOf(functionTypeMap.get("name")));
                }
            }
        }
        modelMap.put("function",functionMapList);
        modelMap.put("functionType",functionTypeMapList);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "function/index";
    }

    /**
     * 新增或者编辑功能权限
     * @param request
     * @param response
     * @param modelMap
     * @param principal
     * @throws IOException
     */
    @RequestMapping(value = "/addFunction")
    public void addFunction(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap, Principal principal) throws IOException {
        String functionType = request.getParameter("function_type");
        String functionUrl = request.getParameter("function_url");
        String functionName = request.getParameter("function_name");
        Function function = new Function();
        function.setType(functionType);
        function.setUrl(functionUrl);
        function.setName(functionName);
        if(request.getParameter("function_id").equals("")) {
            functionService.createFunction(function);
        }else {
            Integer functionId = Integer.valueOf(request.getParameter("function_id"));
            function.setId(functionId);
            functionService.updateFunctionById(function);
        }
        response.sendRedirect("/function/index");
    }

    @RequestMapping(value = "/deleteFunction")
    public void deleteFunction(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,Principal principal) throws IOException {
        Integer functionId = Integer.valueOf(request.getParameter("functionId"));
        functionService.deleteFunctionById(functionId);
        response.sendRedirect("/function/index");
    }

    @RequestMapping(value = "/searchFunction")
    public String searchFunction(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,Principal principal) throws IOException {
        String searchFunctionName = request.getParameter("searchFunctionName");
        List<Map<String,Object>> functionMapList = functionService.searchFunctionByName(searchFunctionName);
        List<Map<String,Object>> functionTypeMapList = functionService.getFunctionTypeList();
        for(Map<String,Object> functionMap : functionMapList){
            for(Map<String,Object> functionTypeMap : functionTypeMapList) {
                if(String.valueOf(functionMap.get("type")).equals(String.valueOf(functionTypeMap.get("code")))){
                    functionMap.put("typeName",String.valueOf(functionTypeMap.get("name")));
                }
            }
        }
        modelMap.put("function",functionMapList);
        modelMap.put("functionType",functionTypeMapList);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "function/index";
    }


}