package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.ConsumablesMapper;
import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.service.ConsumablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConsumablesServiceImpl implements ConsumablesService {

    @Autowired
    ConsumablesMapper consumablesMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return consumablesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(Consumables record) {
        return consumablesMapper.insertSelective(record);
    }

    @Override
    public Consumables selectByPrimaryKey(Integer id) {
        return consumablesMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Consumables record) {
        return consumablesMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Consumables> queryConsumablesByCurId(Integer curriculumId) {
        return consumablesMapper.queryConsumablesByCurId(curriculumId);
    }
}
