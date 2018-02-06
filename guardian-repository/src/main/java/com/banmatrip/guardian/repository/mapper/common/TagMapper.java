package com.banmatrip.guardian.repository.mapper.common;


import com.banmatrip.guardian.domain.Tag;

import java.util.List;
import java.util.Map;

public interface TagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    List<Map<String,Object>> getTagByGroupId(Integer groupId);

    List<Tag> selectAllTag();

    List<Integer> selectByDestinationString(List<String> destinationString);

}