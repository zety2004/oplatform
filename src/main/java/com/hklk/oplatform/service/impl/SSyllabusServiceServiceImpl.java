package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.SCApplyMapper;
import com.hklk.oplatform.dao.inter.SSyllabusMapper;
import com.hklk.oplatform.entity.table.SCApply;
import com.hklk.oplatform.entity.table.SStudent;
import com.hklk.oplatform.entity.table.SSyllabus;
import com.hklk.oplatform.service.SStudentService;
import com.hklk.oplatform.service.SSyllabusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SSyllabusServiceServiceImpl implements SSyllabusService {

    @Autowired
    SSyllabusMapper sSyllabusMapper;
    @Autowired
    SCApplyMapper scApplyMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sSyllabusMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertOrUpdateByPrimaryKeySelective(SSyllabus sSyllabus, SCApply scApply) {
        scApplyMapper.updateByPrimaryKeySelective(scApply);
        if (sSyllabus.getId() != null) {
            return sSyllabusMapper.updateByPrimaryKeySelective(sSyllabus);
        } else {
            return sSyllabusMapper.insertSelective(sSyllabus);
        }
    }
}
