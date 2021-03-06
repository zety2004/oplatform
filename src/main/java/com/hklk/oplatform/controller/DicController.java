package com.hklk.oplatform.controller;

import com.hklk.oplatform.service.DicService;
import com.hklk.oplatform.util.ResultUtils;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequestMapping("/getDic")
@Controller
public class DicController extends BaseController {

    @Autowired
    DicService dicService;

    @ResponseBody
    @RequestMapping("/queryDicList")
    public String queryDices(String typeCode, HttpServletRequest request,
                             HttpServletResponse response, HttpSession session) {
        List<Map<String, Object>> dicList = dicService.queryForList(typeCode);
        return ResultUtils.successStr(dicList);
    }
}
