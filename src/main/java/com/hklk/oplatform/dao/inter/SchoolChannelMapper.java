package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.Channel;
import com.hklk.oplatform.entity.table.ChannelCurriculum;
import com.hklk.oplatform.entity.table.ChannelSchool;

import java.util.List;
import java.util.Map;

public interface SchoolChannelMapper {
    int queryChannelBySign(String sign);

    int insertChannel(Channel bean);

    int insertChannelSchool(ChannelSchool bean);

    int insertChannelCurriculum(ChannelCurriculum bean);

    int delChannel(Integer id);

    int delChannelSchoolBySchoolId(Integer id);

    int delChannelCurriculumByChannelId(Integer id);

    int updateChannel(Channel bean);

    List<Map<String, Object>> queryChannels();

    List<Map<String, Object>> queryChannelCurriculumById(Integer id);

    List<Map<String, Object>> queryCurriculumAll();
}