package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Channel;
import com.hklk.oplatform.entity.table.ChannelCurriculum;
import com.hklk.oplatform.entity.table.ChannelSchool;

import java.util.List;
import java.util.Map;

public interface SchoolChannelService {
    int queryChannelBySign(String sign);

    int insertChannel(Channel bean);

    int insertChannelSchool(ChannelSchool bean);

    int insertChannelCurriculum(ChannelCurriculum bean);

    int delChannel(Integer id);

    int delChannelSchoolBySchoolId(Integer id);

    int delChannelCurriculumByChannelId(Integer id);

    int updateChannel(Channel bean);

    List<Map<String, Object>> queryChannels(String name, Integer status);

    List<Map<String, Object>> queryChannelCurriculumById(Integer id);

    List<Map<String, Object>> queryCurriculumAll(String name, String grade, String learningStyle, String subject);
}
