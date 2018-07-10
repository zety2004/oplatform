package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.vo.CurriculumForListVo;

import java.util.List;
import java.util.Map;

public interface CurriculumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Curriculum record);

    int insertSelective(Curriculum record);

    Map<String,Object> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Curriculum record);

    int updateByPrimaryKeyWithBLOBs(Curriculum record);

    int updateByPrimaryKey(Curriculum record);

    List<CurriculumForListVo> queryCurriculums(Curriculum curriculum);

    Curriculum selectIdByUniqueNum(String uniqueNum);
}