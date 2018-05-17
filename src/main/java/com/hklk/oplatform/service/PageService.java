package com.hklk.oplatform.service;

import com.hklk.oplatform.entity.table.PPage;


import java.util.List;


public interface PageService {
    /**
    * @author 曹良峰
    * @Description 查询页面列表
    * @Date 16:39 2018/5/16
    * @Param []
    * @Return java.util.List<com.hklk.oplatform.entity.table.PPage>
    **/
    List<PPage> queryPages();

    int addPage(PPage PPage);

    int updatePage(PPage PPage);

    int deletePage(Integer id);
}
