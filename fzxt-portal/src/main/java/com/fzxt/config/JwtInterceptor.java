package com.fzxt.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fzxt.config.JwtUtil;
import com.fzxt.response.Result;
import com.fzxt.response.StatusCode;
import com.github.pagehelper.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                final String token = header.substring(7);
                String userId = jwtUtil.checkExpireToken(token);
                if(StringUtil.isEmpty(userId)) {//非正常过期Token请求时，返回错误信息，需重新登录
                    returnJson(response,Result.resultErr(StatusCode.JWTTOKENEXPIRE));
                    return false;
                }else if(userId.endsWith("---1")){ //濒死Token或正常过期Token请求时，获取一个正常Token并返回
                    String newToken = jwtUtil.getGoodToken(userId.substring(0,userId.length()-4));
                    returnJson(response,Result.resultOk(newToken));
                    return false;
                }
                return true;
            }catch (Exception e) {
                e.printStackTrace();
                returnJson(response,Result.resultErr(StatusCode.ACCESSERROR));
                return false;
            }
        } else{
            returnJson(response,Result.resultErr(StatusCode.ACCESSERROR));
            return false;
        }

    }

    private void returnJson(HttpServletResponse response, Result result) throws Exception{
        String json =  JSON.toJSONString(result);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        writer = response.getWriter();
        writer.print(json);
    }
}
