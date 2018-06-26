package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.FeedBack;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.List;
import java.util.Map;

public interface FeedBackService {

    int insertSelective(FeedBack record);

    int updateByPrimaryKeySelective(FeedBack record);

    PageTableForm<Map<String, Object>> queryFeedBackList(Integer sign,Integer pageNum,Integer pageSize);

    Map<String, String> selectFeedBack(Integer id,String tableName);
}
