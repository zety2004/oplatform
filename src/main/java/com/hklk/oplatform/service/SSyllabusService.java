package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SSyllabus;

public interface SSyllabusService {
    /**
     * @author 曹良峰
     * @Description 删除课程表
     * @Date 18:45 2018/5/29
     * @Param [id]
     * @Return int
     **/
    int deleteByPrimaryKey(Integer id);

    int insertOrUpdateByPrimaryKeySelective(SSyllabus sSyllabus, SCApply scApply);
}
