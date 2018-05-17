package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.vo.PageTableForm;


public interface CurriculumService {

    /**
     * @author 曹良峰
     * @Description 查询课程列表
     * @Date 16:25 2018/5/16
     * @Param []
     * @Return java.util.List<com.hklk.oplatform.entity.table.PPage>
     **/
    PageTableForm<Curriculum> queryCurriculums(int pageNum, int pageSize);

    Curriculum selectByPrimaryKey(Integer id);

    int addCurriculum(Curriculum curriculum);

    int updateCurriculum(Curriculum curriculum);

    int deleteCurriculum(Integer id);
}
