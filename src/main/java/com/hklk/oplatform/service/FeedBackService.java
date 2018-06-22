package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.FeedBack;
import com.hklk.oplatform.entity.table.PPage;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.entity.vo.PageTableForm;

import java.util.List;

public interface FeedBackService {

    int insertSelective(FeedBack record);
}
