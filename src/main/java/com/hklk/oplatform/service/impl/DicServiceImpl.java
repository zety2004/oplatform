package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.DicMapper;
import com.hklk.oplatform.entity.table.Dic;
import com.hklk.oplatform.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class DicServiceImpl implements DicService {

    @Autowired
    DicMapper dicMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return dicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(Dic record) {
        return dicMapper.insertSelective(record);
    }

    @Override
    public List<Map<String,Object>> queryForList(String typeCode) {
        return dicMapper.queryForList(typeCode);
    }

    @Override
    public int updateByPrimaryKeySelective(Dic record) {
        return dicMapper.updateByPrimaryKeySelective(record);
    }
}
