package com.fzxt.dsflogin;


import com.alibaba.fastjson.JSONObject;
import com.fzxt.model.User;
import com.fzxt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class QQAuthController {
    private Logger logger = LoggerFactory.getLogger(QQAuthController.class);
    @Autowired
    private QQAuthService qqAuthService;
    @Autowired
    private UserService userService;

    //访问登陆页面，然后会跳转到 QQ 的登陆页面
    @RequestMapping(value = "/qqLoginPage", method = RequestMethod.GET)
    public String qqLogin() throws Exception {
        String uri = qqAuthService.getAuthorizationUrl();
        return uri;
    }

    //qq授权后会回调此方法，并将code传过来
    @RequestMapping("/qq")
    public void getQQCode(String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //根据code获取token
        String accessToken = qqAuthService.getAccessToken(code);
        // 保存 accessToken 到 cookie，过期时间为 30 天，便于以后使用
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setMaxAge(60 * 24 * 30);
        response.addCookie(cookie);
        //本网站是将用户的唯一标识存在用户表中，大家也可以加一张表，存储用户和QQ的对应信息。
        //根据openId判断用户是否已经绑定过
        String openId = qqAuthService.getOpenId(accessToken);
        User user = userService.getOneByField("qqopenId",openId);
        if (user == null) {
            //如果用户不存在，则跳转到绑定页面
           // response.sendRedirect(request.getContextPath() + "/student/html/index.min.html#/bind?type="+Constants.LOGIN_TYPE_QQ);
        } else {
            //如果用户已存在，则直接登录
          //  response.sendRedirect(request.getContextPath() + "/student/html/index.min.html#/app/home?open_id=" + openId);
        }
    }
}