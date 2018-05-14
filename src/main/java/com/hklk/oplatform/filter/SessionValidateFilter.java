package com.hklk.oplatform.filter;

import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import com.hklk.oplatform.util.dwz.ServerInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionValidateFilter implements Filter {
    private static final Log log = LogFactory.getLog(SessionValidateFilter.class);


    private static final String PARAM_URI = "uri";

    private static final String LOGIN_URL = "loginUrl";

    private String loginUrl;


    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        User user = (User) request.getSession().getAttribute("user");


        if (user == null) {
            HttpServletResponse response = (HttpServletResponse) res;
            if (ServerInfo.isAjax(request) || request.getParameter("ajax") != null) {
                PrintWriter out = response.getWriter();
                String json = ToolUtil.buildDWZResultStr(StatusCode.OVER_TIME, StatusCode.getStatusMsg(StatusCode.OVER_TIME), "", "", "", "", "");
                out.print(json);
            } else {
                response.sendRedirect("/jsp/login.jsp");
            }
            return;
        }

        log.debug("validate authentication finished, the authentication has permission to enter this uri.");
        chain.doFilter(req, res);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
