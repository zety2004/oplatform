package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.ParentMessageMapper;
import com.hklk.oplatform.dao.inter.TeacherMessageMapper;
import com.hklk.oplatform.entity.table.ParentMessage;
import com.hklk.oplatform.entity.table.TeacherMessage;
import com.hklk.oplatform.service.ParentMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParentMessageServiceImpl implements ParentMessageService {

    @Autowired
    ParentMessageMapper parentMessageMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return parentMessageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(ParentMessage parentMessage) {
        return parentMessageMapper.insertSelective(parentMessage);
    }

    @Override
    public ParentMessage selectByPrimaryKey(Integer id) {
        return parentMessageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ParentMessage parentMessage) {
        return parentMessageMapper.updateByPrimaryKeySelective(parentMessage);
    }

    @Override
    public int updateIsReadByStudentId(Integer studentId) {
        return parentMessageMapper.updateIsReadByStudentId(studentId);
    }

    @Override
    public int updateIsReadById(Integer id) {
        return parentMessageMapper.updateIsReadById(id);
    }

    @Override
    public List<ParentMessage> queryParentMessage(Integer studentId,Integer isRead) {
        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        params.put("isRead", isRead);
        return parentMessageMapper.queryParentMessage(params);
    }
}
