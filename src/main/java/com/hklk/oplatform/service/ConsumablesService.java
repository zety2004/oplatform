package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Consumables;

import java.util.List;

public interface ConsumablesService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Consumables record);

    Consumables selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Consumables record);

    List<Consumables> queryConsumablesByCurId(Integer curriculumId);
}
