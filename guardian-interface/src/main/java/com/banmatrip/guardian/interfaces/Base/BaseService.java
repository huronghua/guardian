package com.banmatrip.guardian.interfaces.Base;

import com.banmatrip.guardian.domain.OrderPlatform;

import java.util.List;
import java.util.Map;

public interface BaseService {
    List<Map<String,Object>> getDictionaryData(Integer type);
    List<Map<String,Object>> orderPlatformShow(List<Map<String,Object>> orderPlatform, List<Integer> orderPlatformNow);
}
