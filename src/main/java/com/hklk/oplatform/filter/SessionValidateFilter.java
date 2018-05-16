package com.hklk.oplatform.filter;

import com.hklk.oplatform.comm.SsoResultCode;
import com.hklk.oplatform.entity.table.User;
import com.hklk.oplatform.service.AuthenticationRpcService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionValidateFilter extends ClientFilter implements Filter {
    private static final Log log = LogFactory.getLog(SessionValidateFilter.class);

    private String excludedPages;
    private String[] excludedPageArray;

    @Override
    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = getLocalToken(request);
        if (token == null) {
            return false;
        } else if (authenticationRpcService.validate(token)) {// 验证token是否有效
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取请求头中的token
     *
     * @param request
     * @return
     */
    private String getLocalToken(HttpServletRequest request) {
        String token = request.getHeader("Access-Toke");
        return token == null ? null : token;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        boolean isExcludedPage = false;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        for (String page : excludedPageArray) {
            if (request.getServletPath().equals(page)) {
                isExcludedPage = true;
                break;
            }
        }

        if (isExcludedPage) {
            chain.doFilter(request, response);
        } else {


            if (!isAccessAllowed(request, response)) {
                PrintWriter out = response.getWriter();
                if (isAjaxRequest(request) || request.getParameter("ajax") != null) {
                    String json = ToolUtil.buildResultStr(StatusCode.OVER_TIME, StatusCode.getStatusMsg(StatusCode.OVER_TIME));
                    out.print(json);
                } else {
                    String json = ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
                    out.print(json);
                }
                return;
            }
            log.debug("validate authentication finished, the authentication has permission to enter this uri.");
            chain.doFilter(req, res);
        }
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedPages = filterConfig.getInitParameter("excludedPages");
        if (StringUtils.isNotEmpty(excludedPages)) {
            excludedPageArray = excludedPages.split(",");
        }
        return;
    }


    @Override
    public void destroy() {

    }
}
