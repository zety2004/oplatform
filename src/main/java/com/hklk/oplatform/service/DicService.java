package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Dic;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.List;

public interface DicService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Dic record);

    List<Dic> queryForList(String typeCode);

    int updateByPrimaryKeySelective(Dic record);
}
