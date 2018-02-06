package com.banmatrip.guardian.repository.mapper.common;


import com.banmatrip.guardian.domain.ProductOrderTag;

import java.util.List;
import java.util.Map;

public interface ProductOrderTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductOrderTag record);

    int insertSelective(ProductOrderTag record);

    ProductOrderTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductOrderTag record);

    int updateByPrimaryKey(ProductOrderTag record);

    List<Map> getProductOrderTagList(Integer type);

    List<ProductOrderTag> selectAllProductOrderTag();

    List<Integer> selectByProductTypeString(List<String> productTypeString);
}