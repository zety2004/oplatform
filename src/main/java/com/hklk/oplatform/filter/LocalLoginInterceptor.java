package com.hklk.oplatform.filter;

import com.hklk.oplatform.filter.repo.LocalLoginRepository;
import com.hklk.oplatform.service.AuthenticationRpcService;
import com.hklk.oplatform.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class LocalLoginInterceptor implements HandlerInterceptor {
    @Autowired
    AuthenticationRpcService authenticationRpcService;

    private String getLocalToken(HttpServletRequest request) {
        String token = request.getHeader("Access-Toke");
        return token == null ? null : token;
    }

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

    protected void responseJson(HttpServletResponse response, int code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        PrintWriter writer = response.getWriter();
        writer.write(new StringBuilder().append("{\"resultCode\":").append(code).append(",\"resultMsg\":\"").append(message)
                .append("\"}").toString());
        writer.flush();
        writer.close();
    }


    //Action之前执行:
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, DELETE, OPTIONS");
        response.setHeader("X-Powered-By", "3.2.1");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type, Access-Control-Allow-Headers, Authorization, Access-Toke");
        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            response.setStatus(204);
        }

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LocalLoginRepository classAnnotation = handlerMethod.getBean().getClass().getAnnotation(LocalLoginRepository.class);
        // 有 @LocalLoginRepository 注解，需要认证
        if (null != classAnnotation) {
            if (!isAccessAllowed(request, response)) {
                responseJson(response, StatusCode.SSO_TOKEN_ERROR, StatusCode.getStatusMsg(StatusCode.SSO_TOKEN_ERROR));
                return false;
            }
        }
        return true;
    }


    //生成视图之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    //最后执行，可用于释放资源
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
    }
}
