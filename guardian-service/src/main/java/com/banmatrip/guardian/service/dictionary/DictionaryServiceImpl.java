package com.banmatrip.guardian.service.dictionary;

import com.banmatrip.guardian.domain.Dictionary;
import com.banmatrip.guardian.interfaces.dictionary.DictionaryService;
import com.banmatrip.guardian.repository.mapper.rolepermission.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * author:zhangwei
 * Created by banma on 2017/12/18.
 */
@Service
public class DictionaryServiceImpl implements DictionaryService{

    @Autowired
    DictionaryMapper dictionaryMapper;

    /**可见变量**/
    private volatile boolean newDictFlag;

    @Override
    public List<Map<String, Object>> getAllDictionary() {
        List<Map<String, Object>> dictionaryList =dictionaryMapper.getDictionaryList();
        return dictionaryList;
    }

    /**
     * 新增数据字典
     *
     * @param dictionary
     * @return
     */
    @Override
    public Integer createDictionary(Dictionary dictionary) {
        try {
            if (newDictFlag) {
                throw new RuntimeException("请稍后操作，有人在新增数据字典!");
            } else{
                synchronized (this) {
                    newDictFlag = true;
                    return dictionaryMapper.createDictionary(dictionary);
                }
            }
        } finally {
            /**状态回位**/
            newDictFlag = false;
        }
    }

    @Override
    public int deleteDictionaryById(Integer dictionaryId) {
        return dictionaryMapper.deleteByPrimaryKey(dictionaryId);
    }

    @Override
    public int updateDictionaryById(Dictionary dictionary) {
        return dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    @Override
    public List<Map<String, Object>> searchDictionaryByName(String searchtext) {
        return dictionaryMapper.searchDictionaryByName(searchtext);
    }

    @Override
    public Integer selectCountByTypeAndName(Dictionary dictionary) {
        return dictionaryMapper.selectCountByTypeAndName(dictionary);
    }

    @Override
    public List<Map<String, Object>> getDictionaryType() {
        return dictionaryMapper.getDictionaryType();
    }
}
