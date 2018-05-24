package com.hklk.oplatform.filter;

import com.hklk.oplatform.comm.cache.RedisCache;
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

public class RoleValidateFilter implements Filter {
    private static final Log log = LogFactory.getLog(RoleValidateFilter.class);

    private String excludedPages;
    private String[] excludedPageArray;

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
            if (!false) {
                String uri = ((HttpServletRequest) req).getRequestURI();
                RedisCache.get("");

                PrintWriter out = response.getWriter();
                String json = ToolUtil.buildResultStr(StatusCode.ERROR, StatusCode.getStatusMsg(StatusCode.ERROR));
                out.print(json);
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
