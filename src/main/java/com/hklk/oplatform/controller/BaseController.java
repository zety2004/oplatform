package com.hklk.oplatform.controller;

import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import com.hklk.oplatform.util.editor.DateEditor;
import com.hklk.oplatform.util.editor.DoubleEditor;
import com.hklk.oplatform.util.editor.IntegerEditor;
import com.hklk.oplatform.util.editor.LongEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public abstract class BaseController {

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {

        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(double.class, new DoubleEditor());
        binder.registerCustomEditor(Date.class, new DateEditor());
    }


    @ExceptionHandler(Exception.class)
    public String exception(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        request.setAttribute("exception", e);
/*		if (ServerInfo.isAjax(request) || request.getParameter("ajax") != null) {
			return ToolUtil.buildResultStr(StatusCode.SYS_ERROR, StatusCode.getStatusMsg(StatusCode.SYS_ERROR));
		}
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("statusCode", 300);
		mav.addObject("message", e.getMessage());*/

        return ToolUtil.buildResultStr(StatusCode.SYS_ERROR, StatusCode.getStatusMsg(StatusCode.SYS_ERROR));
    }
}
