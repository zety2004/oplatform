package com.hklk.oplatform.filter;

import com.hklk.oplatform.service.AuthenticationRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class BaseInterceptor {
    @Autowired
    AuthenticationRpcService authenticationRpcService;

    protected String getLocalToken(HttpServletRequest request) {
        String token = request.getHeader("Access-Toke");
        return token == null ? null : token;
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


}
