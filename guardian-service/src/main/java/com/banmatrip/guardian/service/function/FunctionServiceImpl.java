package com.banmatrip.guardian.service.function;

import com.banmatrip.guardian.domain.Function;
import com.banmatrip.guardian.interfaces.function.FunctionService;
import com.banmatrip.guardian.repository.mapper.rolepermission.DictionaryMapper;
import com.banmatrip.guardian.repository.mapper.rolepermission.FunctionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-07 16:59
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/

@Service
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    FunctionMapper functionMapper;

    @Autowired
    DictionaryMapper dictionaryMapper;

    @Override
    public Integer createFunction(Function function) {
        return functionMapper.insertSelective(function);
    }

    @Override
    public List<Map<String, Object>> getAllFunction() {
        return functionMapper.getAllFunction();
    }

    @Override
    public List<Map<String,Object>> getFunctionTypeList() {
        return functionMapper.getFunctionTypeList();
    }

    @Override
    public int deleteFunctionById(Integer functionId) {
        return functionMapper.deleteByPrimaryKey(functionId);
    }

    @Override
    public int updateFunctionById(Function function) {
        return functionMapper.updateByPrimaryKeySelective(function);
    }

    @Override
    public List<Map<String, Object>> searchFunctionByName(String searchtext) {
        return functionMapper.searchFunctionByName(searchtext);
    }
}