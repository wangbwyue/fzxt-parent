package com.fzxt.config;

import com.fzxt.redis.RedisUtil;
import com.github.pagehelper.util.StringUtil;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/4/11.
 * 正常Token：Token未过期，且未达到建议更换时间。
 *   濒死Token：Token未过期，已达到建议更换时间。
 *   正常过期Token：Token已过期，但存在于redis中。
 *   非正常过期Token：Token已过期，不存在于redis中。
 *
 *   正常Token传入
 *   当正常Token请求时，返回当前Token。
 *
 * 濒死Token传入
 *   当濒死Token请求时，获取一个正常Token并返回。
 *
 * 正常过期Token
 *   当正常过期Token请求时，获取一个正常Token并返回。
 *
 * 非正常过期过期Token
 *   当非正常过期Token请求时，返回错误信息，需重新登录。
 */
@Component
public class JwtUtil {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.ttl}")
    private long ttl;//token有效时间默认15分钟

    @Value("${jwt.fresh_token_time}")
    private long fresh_token_time; //token还剩余5分钟时，刷新token

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private RedisUtil redisUtil;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param userId 用户名
     * @return
     */
    public String createJWT(String userId) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
                .setSubject(userId)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }

        System.out.println("结束时间========================="+sdf.format(new Date(nowMillis + ttl)));
        String newToken = builder.compact();
        //新token初始化
        redisUtil.set("userJwtToken_"+userId, newToken,fresh_token_time*1000);
        return newToken;
    }

    /**
     * 解析JWT
     * 获取jwt的payload部分
     * @param token
     * @return
     */
    public Claims parseJWT(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * 从token中获取userId
     */
    public String getUserFromToken(String token) {
        return parseJWT(token).getSubject();
    }


    /**
     * 获取可用的token
     * 如该用户当前token可用，即返回
     * 当前token不可用，则返回一个新token
     * @param userId
     * @return
     */
    public String getGoodToken(String userId){
        String token = redisUtil.get("userJwtToken_"+userId).toString();
        boolean flag = this.checkToken(token);
        //校验当前token能否使用，不能使用则生成新token
        if(flag){
            return token;
        }else{
            String newToken = this.createJWT(userId);
            return newToken;
        }
    }

    /**
     * 判断过期token是否合法
     * 正常Token-----返回 userId （ 当正常Token请求时，返回当前Token。）
     * 非正常过期过期Token，redis没有返回"";（当非正常过期Token请求时，返回错误信息，需重新登录。）
     * 濒死Token或正常过期Token，redis存在返回userId + "---1";（当濒死Token请求时，获取一个正常Token并返回。）
     * @param token
     * @return
     */
    public String checkExpireToken(String token){
        //判断token是否需要更新
        boolean expireFlag = this.checkToken(token);
        String userId = this.getUserFromToken(token);
        //false：不建议使用
        if(!expireFlag){
            String token2 = redisUtil.get("userJwtToken_"+userId).toString();
            if(StringUtil.isNotEmpty(token2)){
                userId = userId + "---1";
            } else{
                userId ="";
            }
        }
        return userId;
    }

    /**
     * 检查当前token是否还能继续使用
     * true：可以  false：不建议
     * @param token
     * @return
     */
    public boolean checkToken(String token){
        try {
            // jwt正常情况 则判断失效时间是否大于5分钟
            long expireTime = parseJWT(token).getExpiration().getTime();
            long diff = expireTime - System.currentTimeMillis();
            //如果有效期小于5分钟，则不建议继续使用该token
            System.out.println("结束时间========时间差================="+diff);
            System.out.println("结束时间==========目标==============="+fresh_token_time);
            if (diff < fresh_token_time) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
