package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.ConsumablesMapper;
import com.hklk.oplatform.dao.inter.CurriculumMapper;
import com.hklk.oplatform.entity.table.Consumables;
import com.hklk.oplatform.entity.table.Curriculum;
import com.hklk.oplatform.entity.table.CurriculumInsertVo;
import com.hklk.oplatform.entity.vo.CurriculumForListVo;
import com.hklk.oplatform.entity.vo.CurriculumVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.CurriculumService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CurriculumServiceImpl implements CurriculumService {

    @Autowired
    CurriculumMapper curriculumMapper;
    @Autowired
    ConsumablesMapper consumablesMapper;

    @Override
    public PageTableForm<CurriculumForListVo> queryCurriculums(Curriculum curriculum, int pageNum, int pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        curriculumMapper.queryCurriculums(curriculum);
        PageTableForm<CurriculumForListVo> pageTableForm = new PageTableForm(page);
        return pageTableForm;
    }

    @Override
    public Map<String, Object> selectByPrimaryKey(Integer id) {
        Map<String, Object> curriculum = curriculumMapper.selectByPrimaryKey(id);
        if (curriculum.get("des") != null) {
            curriculum.put("des", new String((byte[]) curriculum.get("des")));
        }
        if (curriculum.get("wxdes") != null) {
            curriculum.put("wxdes", new String((byte[]) curriculum.get("wxdes")));
        }
        List<Consumables> consumables = consumablesMapper.queryConsumablesByCurId((Integer) curriculum.get("id"));
        curriculum.put("consumables", consumables);
        return curriculum;
    }

    @Override
    public int addCurriculum(CurriculumInsertVo curriculum) {
        return curriculumMapper.insertSelective(curriculum);
    }

    @Override
    public int updateCurriculum(CurriculumInsertVo curriculum) {
        return curriculumMapper.updateByPrimaryKeySelective(curriculum);
    }

    @Override
    public int deleteCurriculum(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return curriculumMapper.deleteByPrimaryKey(params);
    }

    @Override
    public int deleteCurriculum(Integer id, Integer teacherId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("teacherId", teacherId);
        return curriculumMapper.deleteByPrimaryKey(params);
    }

    @Override
    public int selectIdByUniqueNum(String uniqueNum) {
        return curriculumMapper.selectIdByUniqueNum(uniqueNum);
    }

    public static void main(String[] args) {

    }
}
