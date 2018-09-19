package com.hklk.oplatform.filter;

import com.hklk.oplatform.comm.LoginSchool;
import com.hklk.oplatform.comm.TokenManager;
import com.hklk.oplatform.filter.repo.SchoolLoginRepository;
import com.hklk.oplatform.service.AuthenticationRpcService;
import com.hklk.oplatform.util.StatusCode;
import com.hklk.oplatform.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class SchoolLoginInterceptor extends BaseInterceptor implements HandlerInterceptor {
    @Autowired
    AuthenticationRpcService authenticationRpcService;



    private boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = getLocalToken(request);
        if (token == null) {
            return false;
        } else if (authenticationRpcService.validate(TokenManager.schoolTokenKey, token, LoginSchool.class)) {// 验证token是否有效
            return true;
        } else {
            return false;
        }
    }

    //Action之前执行:
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        SchoolLoginRepository classAnnotation = handlerMethod.getBean().getClass().getAnnotation(SchoolLoginRepository.class);
        // 有 @SchoolLoginRepository 注解，需要认证
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
