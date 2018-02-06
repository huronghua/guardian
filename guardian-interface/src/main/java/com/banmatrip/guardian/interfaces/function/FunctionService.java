package com.banmatrip.guardian.interfaces.function;

import com.banmatrip.guardian.domain.Function;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * @author Eric-hu
 * @Description:
 * @create 2017-12-07 16:28
 * @Copyright: 2017 www.banmatrip.com All rights reserved.
 **/
public interface FunctionService {
    Integer createFunction(Function function);

    List<Map<String,Object>> getAllFunction();

    List<Map<String,Object>> getFunctionTypeList();

    int deleteFunctionById(Integer functionId);

    int updateFunctionById(Function function);

    List<Map<String,Object>> searchFunctionByName(String searchtext);
}