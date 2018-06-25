package com.hklk.oplatform.service.impl;

import com.hklk.oplatform.dao.inter.DicMapper;
import com.hklk.oplatform.dao.inter.FeedBackMapper;
import com.hklk.oplatform.entity.table.FeedBack;
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
    public List<FeedBack> queryFeedBackList() {
        return feedBackMapper.queryFeedBackList();
    }

    @Override
    public Map<String, String> selectFeedBack(Integer id, String tableName) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("tableName", tableName);
        return feedBackMapper.selectFeedBack(param);
    }
}
