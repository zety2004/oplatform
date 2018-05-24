package com.hklk.oplatform.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddHeadFilter implements Filter {
    private static final Log log = LogFactory.getLog(AddHeadFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, DELETE, OPTIONS");
        response.setHeader("X-Powered-By","3.2.1");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type, Access-Control-Allow-Headers, Authorization, Access-Toke");
        String method= ((HttpServletRequest) req).getMethod();
        if (method.equals("OPTIONS")){
            response.setStatus(204);
        }
        log.debug("允许跨域访问");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
