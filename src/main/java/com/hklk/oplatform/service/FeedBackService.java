package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.FeedBack;

import java.util.List;
import java.util.Map;

public interface FeedBackService {

    int insertSelective(FeedBack record);

    List<FeedBack> queryFeedBackList();

    Map<String, String> selectFeedBack(Integer id,String tableName);
}
