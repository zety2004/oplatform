package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.CurriculumInsertVo;
import com.hklk.oplatform.entity.vo.CurriculumForListVo;

import java.util.List;
import java.util.Map;

public interface CurriculumMapper {
    int deleteByPrimaryKey(Map<String, Object> params);

    int insertSelective(CurriculumInsertVo record);

    Map<String, Object> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CurriculumInsertVo record);

    List<CurriculumForListVo> queryCurriculums(Curriculum curriculum);

    int selectIdByUniqueNum(String uniqueNum);
}