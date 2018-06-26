package com.hklk.oplatform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hklk.oplatform.dao.inter.DicMapper;
import com.hklk.oplatform.dao.inter.FeedBackMapper;
import com.hklk.oplatform.entity.table.FeedBack;
import com.hklk.oplatform.entity.vo.CurriculumForListVo;
import com.hklk.oplatform.entity.vo.PageTableForm;
import com.hklk.oplatform.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Autowired
    FeedBackMapper feedBackMapper;

    @Override
    public int insertSelective(FeedBack record) {
        return feedBackMapper.insertSelective(record);
    }

    @Override
    public PageTableForm<Map<String, Object>> queryFeedBackList(Integer pageNum, Integer pageSize) {
        Page page = PageHelper.startPage(pageNum, pageSize, true);
        feedBackMapper.queryFeedBackList();
        PageTableForm<Map<String, Object>> pageTableForm = new PageTableForm(page);

        for (Map<String, Object> obj : pageTableForm.getObjList()) {
            Map<String, String> temp = selectFeedBack((Integer) obj.get("id"), (String) obj.get("tableName"));
            if(temp!=null){
                obj.remove("tableName");
                obj.putAll(temp);
            }
        }
        return pageTableForm;
    }

    @Override
    public Map<String, String> selectFeedBack(Integer id, String tableName) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("tableName", tableName);
        return feedBackMapper.selectFeedBack(param);
    }
}
