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
import java.util.Date;
import java.util.Objects;

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


    @ApiOperation(value="微信登录接口")
    @GetMapping("/login/{code}")
    public Result login(@PathVariable String code) {
        if (StringUtils.isBlank(code)) {
            return Result.resultErr(StatusCode.CODEISNULL);
        }
        //获取open_id,判断用户是否存在，不存在则插入
        Code2Session code2Session = restTemplate.getForObject(miniAppProperties.getLogUrlRest(code), Code2Session.class);
        if(code2Session.getErrcode() == 0){
            String openid =code2Session.getOpenid();
            User user = userService.getOneByField("miniopenId", openid);
            if(user == null){
                user = new User();
                user.setId(IDUtils.getPramaryId());
                user.setMiniopenId(openid);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                user.setCreateTime(sdf.format(new Date()));
                userService.insert(user);
            }
            //结果放入redis
            String token = "MINIAPPLOGIN"+IDUtils.getPramaryId();
            redisUtil.set(token,code2Session);
            return Result.resultOk(token);
        } else{
            return Result.resultErr(code2Session.getErrmsg());
        }
    }

     /**
     * <pre>
     * 解密获取用户信息接口
     * </pre>
     */
    @ApiOperation(value="解密获取用户信息接口")
    @PostMapping("/info")
    public Result info(@RequestBody UserInfoParam userInfoParam) {
        User user =null;
        //从token中获取
        Code2Session code2Session = (Code2Session)redisUtil.get(userInfoParam.getToken());
       if (code2Session == null) {
            return Result.resultErr(StatusCode.JWTTOKENEXPIRE);
        }
        try{
            String result = AesCbcUtil.decrypt(userInfoParam.getEncryptedData(), code2Session.getSession_key(), userInfoParam.getIv(), "UTF-8");
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
                return Result.resultOk(user);
            } else {
                return Result.resultErr(StatusCode.ERRORDECRYPT);
            }
        } catch (Exception e){
            e.printStackTrace();
            return Result.resultErr(e.getMessage());
        }

    }

}
