package com.fzxt.security;

import com.alibaba.fastjson.JSON;
import com.fzxt.response.Result;
import com.fzxt.response.StatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 认证失败处理类，返回401
 * Author: JoeTao
 * createAt: 2018/9/20
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //验证为未登陆状态会进入此方法，认证错误
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        String json = JSON.toJSONString(Result.resultErr(StatusCode.ACCESSERROR));
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}