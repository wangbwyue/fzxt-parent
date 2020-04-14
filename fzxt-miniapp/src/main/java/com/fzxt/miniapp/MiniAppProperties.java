package com.fzxt.miniapp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wx.miniapp")
public class MiniAppProperties {

        private String appid;

        /**
         * 设置微信小程序的Secret
         */
        private String secret;

        /**
         * 设置微信小程序消息服务器配置的token
         */
        private String token;

        /**
         * 设置微信小程序消息服务器配置的EncodingAESKey
         */
        private String aesKey;

        /**
         * 消息格式，XML或者JSON
         */
        private String msgDataFormat;
        //登录接口获得临时登录凭证
        private String loginUrl="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code=JSCODE&grant_type=authorization_code";

        private String accessTokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;


        public String getLogUrlByCode(String code){
            return loginUrl.replace("JSCODE",code);
        }

}
