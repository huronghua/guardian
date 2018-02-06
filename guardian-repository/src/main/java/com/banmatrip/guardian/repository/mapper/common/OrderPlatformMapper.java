package com.banmatrip.guardian.repository.mapper.common;

import com.banmatrip.guardian.domain.OrderPlatform;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface OrderPlatformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderPlatform record);

    int insertSelective(OrderPlatform record);

    OrderPlatform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderPlatform record);

    int updateByPrimaryKey(OrderPlatform record);

    List<Map> getOrderPlatform(@Param(value = "orderPlatformIdList") List<Integer> orderPlatformIdList);

    List<OrderPlatform> selectAllOrderPlatform();

    List<OrderPlatform> selectAllOrderPlatformByParentId(int parentId);

    List<Integer> selectByPlatformString(List<String> platformString);

}