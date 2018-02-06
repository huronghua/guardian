package com.banmatrip.guardian.interfaces.dictionary;


import com.banmatrip.guardian.domain.Dictionary;

import java.util.List;
import java.util.Map;

/**
 * author:zhangwei
 * Created by banma on 2017/12/18.
 */
public interface DictionaryService {

    Integer createDictionary(Dictionary dictionary);

    List<Map<String,Object>> getAllDictionary();

    int deleteDictionaryById(Integer dictionaryId);

    int updateDictionaryById(Dictionary dictionary);

    List<Map<String,Object>> searchDictionaryByName(String searchtext);

    Integer selectCountByTypeAndName(Dictionary dictionary);

    List<Map<String,Object>> getDictionaryType();

}
