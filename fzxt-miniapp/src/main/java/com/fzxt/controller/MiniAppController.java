package com.fzxt.controller;

import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import com.alibaba.fastjson.JSONObject;
import com.fzxt.miniapp.*;
import com.fzxt.model.User;
import com.fzxt.redis.RedisUtil;
import com.fzxt.response.Result;
import com.fzxt.response.StatusCode;
import com.fzxt.service.UserService;
import com.fzxt.utils.IDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Api(value="XcxController",tags={"微信小程序接口"})
@RestController
@RequestMapping("/miniApp")
@Slf4j
public class MiniAppController {

    @Autowired
    private MiniAppProperties miniAppProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @ApiOperation(value="微信小程序服务认证接口")
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        log.debug("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
            signature, timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        if (MiniAppUtils.checkSignature(miniAppProperties.getToken(),timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }

    @ApiOperation(value="接收来自微信服务器的认证消息")
    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature,
                       @RequestParam(name = "encrypt_type", required = false) String encryptType,
                       @RequestParam(name = "signature", required = false) String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {
        log.debug("\n接收微信请求：[msg_signature=[{}], encrypt_type=[{}], signature=[{}]," +
                " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
            msgSignature, encryptType, signature, timestamp, nonce, requestBody);
        final boolean isJson = Objects.equals(miniAppProperties.getMsgDataFormat(), "JSON");
        if (StringUtils.isBlank(encryptType)) {
            // 明文传输的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromJson(requestBody);
            } else {//xml
                inMessage = WxMaMessage.fromXml(requestBody);
            }
            return "success";
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            WxMaMessage inMessage = new WxMaMessage();
            return "success";
        }

        throw new RuntimeException("不可识别的加密类型：" + encryptType);
    }

    public static void main(String[] args) {
        int i=12;
        System.out.println(i+=i-=i*=i);
    }
    @ApiOperation(value="微信登录接口")
    @GetMapping("/login")
    public Result login(String code) {
        String token = "MINIAPPLOGIN"+IDUtils.getPramaryId();
        if (StringUtils.isBlank(code)) {
            return Result.resultErr(StatusCode.CODEISNULL);
        }
        Code2Session code2Session = restTemplate.getForObject(miniAppProperties.getLoginUrl(), Code2Session.class);
        String openid =code2Session.getOpenid();
        User user = userService.getOneByField("miniopenId", openid);
        if(user == null){
            AccessToken accessToken = restTemplate.getForObject(miniAppProperties.getLoginUrl(), AccessToken.class);
            user = new User();
            user.setId(IDUtils.getPramaryId());
            user.setMiniopenId(openid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setCreateTime(sdf.format(new Date()));
            userService.insert(user);
        }
        //结果放入redis
        redisUtil.set(token,user);
        return Result.resultOk(token);
    }
    /**
     * <pre>
     * 解密获取用户信息接口
     * </pre>
     */
    @ApiOperation(value="解密获取用户信息接口")
    @GetMapping("/info")
    public Result info(String token,String encryptedData, String iv) {
        User user =null;
        try{
            String result = AesCbcUtil.decrypt(encryptedData, token, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                JSONObject userInfoJSON = JSONObject.parseObject(result);
                user = userService.getOneByField("miniopenId", userInfoJSON.getString("openId"));
                user.setNickname(userInfoJSON.getString("nickName"));
                user.setGender(userInfoJSON.getString("gender"));
                user.setCity(userInfoJSON.getString("city"));
                user.setProvince(userInfoJSON.getString("province"));
                user.setCountry(userInfoJSON.getString("country"));
                user.setAvatarurl( userInfoJSON.getString("avatarUrl"));
                userService.update(user);
            } else {
                Result.resultErr(StatusCode.ERRORDECRYPT);
            }
        } catch (Exception e){
            e.printStackTrace();
            Result.resultErr(e.getMessage());
        }
        return Result.resultOk(user);
    }

}
