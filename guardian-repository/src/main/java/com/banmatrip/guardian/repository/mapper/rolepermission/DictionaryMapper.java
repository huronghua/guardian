package com.banmatrip.guardian.repository.mapper.rolepermission;

import com.banmatrip.guardian.domain.Dictionary;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

    String selectForPosition(String position);

    List<String> selectForRoleType(List<String> roleTypeList);

    int selectByPositionName(String position);

    String selectByRoleType(String roleType);

    String selectByEthnic(String ethnic);

    List<Map<String,Object>> selectAllDepartmentType();

    List<Map<String,Object>> selectAllResourceType();

    List<Map<String,Object>> getDictionary(@Param("type") int type);

    List<String> selectByResourceTypeString(List<String> resourceTypeString);

    String selectEthnicById(String ethnic);

    List<Map<String, Object>> getDictionaryList();

    List<Map<String, Object>> searchDictionaryByName(String searchtext);

    Integer selectCountByTypeAndName(Dictionary dictionary);

    List<Map<String,Object>> getDictionaryType();

    int createDictionary(Dictionary record);

    List<String> selectByRole(List<String> role);

    Integer selectPlatCount();

    Integer selectProCount();

    Integer selectDesCount();

    Integer selectResCount();

    List<Integer> selectPlatIdList();

    List<Integer> selectProIdList();

    List<Integer> selectDesIdList();

    List<Integer> selectResIdList();

}