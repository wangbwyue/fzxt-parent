package com.fzxt.config;

import com.alibaba.fastjson.JSON;
import com.fzxt.miniapp.Code2Session;
import com.fzxt.redis.RedisUtil;
import com.fzxt.response.Result;
import com.fzxt.response.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class MiniAppInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                final String token = header.substring(7);
                Code2Session code2Session = (Code2Session)redisUtil.get(token);
                if(code2Session !=null){
                    return true;
                } else{
                    returnJson(response,Result.resultErr(StatusCode.JWTTOKENEXPIRE));
                    return false;
                }
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
