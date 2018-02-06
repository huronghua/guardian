package com.banmatrip.guardian.controller;

import com.banmatrip.guardian.common.PermissionRender;
import com.banmatrip.guardian.domain.Dictionary;
import com.banmatrip.guardian.domain.Function;
import com.banmatrip.guardian.interfaces.dictionary.DictionaryService;
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
 * Created by banma on 2017/12/18.
 */
@Controller
@RequestMapping(value = "/dictionary")
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    private PermissionRender permissionRender;

    @RequestMapping(value = "/index")
    public String dictionaryIndex(ModelMap modelMap,Principal principal){
        List<Map<String,Object>> dictionaryList = dictionaryService.getAllDictionary();
        List<Map<String,Object>> dictionaryTypeList = dictionaryService.getDictionaryType();
        modelMap.put("dictionaryList",dictionaryList);
        modelMap.put("dictionaryType",dictionaryTypeList);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "dictionary/index";
    }


    /**
     * 新增或者编辑功能权限
     * @param request
     * @param response
     * @param modelMap
     * @param principal
     * @throws IOException
     */
    @RequestMapping(value = "/addDictionary")
    public String addDictionary(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Principal principal) throws IOException {
        String dictionaryType = request.getParameter("dictionary_type");
        //String dictionaryCode = request.getParameter("dictionary_code");
        String dictionaryName = request.getParameter("dictionary_name");
        Dictionary dictionary=new Dictionary();
        dictionary.setType(dictionaryType);
        //dictionary.setCode(dictionaryCode);
        dictionary.setName(dictionaryName);
        Integer dictionaryCount=dictionaryService.selectCountByTypeAndName(dictionary);
        if(dictionaryCount != null && dictionaryCount.intValue() == 0) {
            if (request.getParameter("dictionary_id").equals("")) {
                dictionaryService.createDictionary(dictionary);
            } else {
                Integer dictionaryId = Integer.valueOf(request.getParameter("dictionary_id"));
                dictionary.setId(dictionaryId);
                dictionaryService.updateDictionaryById(dictionary);
            }
        }else{
            modelMap.put("success","字典名字跟类型重复");
        }
        List<Map<String,Object>> dictionaryList = dictionaryService.getAllDictionary();
        List<Map<String,Object>> dictionaryTypeList = dictionaryService.getDictionaryType();
        modelMap.put("dictionaryList",dictionaryList);
        modelMap.put("dictionaryType",dictionaryTypeList);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "dictionary/index";
    }

    @RequestMapping(value = "/deleteDictionary")
    public void deleteDictionary(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,Principal principal) throws IOException {
        Integer dictionaryId = Integer.valueOf(request.getParameter("dictionaryId"));
        dictionaryService.deleteDictionaryById(dictionaryId);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        response.sendRedirect("/dictionary/index");
    }

    @RequestMapping(value = "/searchDictionary")
    public String searchDictionary(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,Principal principal) throws IOException {
        String searchDictionaryName = request.getParameter("searchDictionaryName");
        List<Map<String,Object>> DictionaryMapList = dictionaryService.searchDictionaryByName(searchDictionaryName);
        List<Map<String,Object>> dictionaryTypeList = dictionaryService.getDictionaryType();
        modelMap.put("dictionaryList",DictionaryMapList);
        modelMap.put("dictionaryType",dictionaryTypeList);
        /**功能权限控制**/
        permissionRender.renderRolePermission(modelMap,principal.getName());
        return "dictionary/index";
    }
}
