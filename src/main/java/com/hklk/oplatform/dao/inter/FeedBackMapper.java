package com.hklk.oplatform.dao.inter;

import com.hklk.oplatform.entity.table.FeedBack;

import java.util.List;
import java.util.Map;

public interface FeedBackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeedBack record);

    int insertSelective(FeedBack record);

    FeedBack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeedBack record);

    int updateByPrimaryKey(FeedBack record);

    List<FeedBack> queryFeedBackList();

    Map<String ,String> selectFeedBack(Map<String, Object> params);
}