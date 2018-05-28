package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Consumables;

import java.util.List;

public interface ConsumablesService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Consumables record);

    Consumables selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Consumables record);

    /**
     * @author 曹良峰
     * @Description
     * @Date 16:05 2018/5/24
     * @Param [curriculumId]
     * @Return java.util.List<com.hklk.oplatform.entity.table.Consumables>
     **/
    List<Consumables> queryConsumablesByCurId(Integer curriculumId);
}
