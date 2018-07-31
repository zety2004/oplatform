package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.CurriculumInsertVo;
import com.hklk.oplatform.entity.vo.CurriculumForListVo;
import com.hklk.oplatform.entity.vo.CurriculumVo;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.Map;


public interface CurriculumService {

    /**
     * @author 曹良峰
     * @Description 查询课程列表
     * @Date 16:25 2018/5/16
     * @Param []
     * @Return java.util.List<com.hklk.oplatform.entity.table.PPage>
     **/
    PageTableForm<CurriculumForListVo> queryCurriculums(Curriculum curriculum,int pageNum, int pageSize);

    /**
     * @author 曹良峰
     * @Description 根据id查询课程详情
     * @Date 16:05 2018/5/24
     * @Param [id]
     * @Return com.hklk.oplatform.entity.vo.CurriculumVo
     **/
    Map<String, Object> selectByPrimaryKey(Integer id);

    int addCurriculum(CurriculumInsertVo curriculum);

    int updateCurriculum(CurriculumInsertVo curriculum);

    int deleteCurriculum(Integer id);

    int selectIdByUniqueNum(String uniqueNum);
}
