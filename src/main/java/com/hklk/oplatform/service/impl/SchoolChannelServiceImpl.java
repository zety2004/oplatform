package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.SchoolChannelMapper;
import com.hklk.oplatform.entity.table.Channel;
import com.hklk.oplatform.entity.table.ChannelCurriculum;
import com.hklk.oplatform.entity.table.ChannelSchool;
import com.hklk.oplatform.service.SchoolChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SchoolChannelServiceImpl implements SchoolChannelService {

    @Autowired
    SchoolChannelMapper schoolChannelMapper;

    @Override
    public int queryChannelBySign(String sign) {
        return schoolChannelMapper.queryChannelBySign(sign);
    }

    @Override
    public int insertChannel(Channel bean) {
        return schoolChannelMapper.insertChannel(bean);
    }

    @Override
    public int insertChannelSchool(ChannelSchool bean) {
        return schoolChannelMapper.insertChannelSchool(bean);
    }

    @Override
    public int insertChannelCurriculum(ChannelCurriculum bean) {
        return schoolChannelMapper.insertChannelCurriculum(bean);
    }

    @Override
    public int delChannel(Integer id) {
        return schoolChannelMapper.delChannel(id);
    }

    @Override
    public int delChannelSchoolBySchoolId(Integer id) {
        return schoolChannelMapper.delChannelSchoolBySchoolId(id);
    }

    @Override
    public int delChannelCurriculumByChannelId(Integer id) {
        return schoolChannelMapper.delChannelCurriculumByChannelId(id);
    }

    @Override
    public int updateChannel(Channel bean) {
        return schoolChannelMapper.updateChannel(bean);
    }

    @Override
    public List<Map<String, Object>> queryChannels(String name, Integer status) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("status", status);
        return schoolChannelMapper.queryChannels(param);
    }

    @Override
    public List<Map<String, Object>> queryChannelCurriculumById(Integer id) {
        return schoolChannelMapper.queryChannelCurriculumById(id);
    }

    @Override
    public List<Map<String, Object>> queryCurriculumAll(String name, String grade, String learningStyle, String subject) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("grade", grade);
        param.put("learningStyle", learningStyle);
        param.put("subject", subject);
        return schoolChannelMapper.queryCurriculumAll(param);
    }
}
