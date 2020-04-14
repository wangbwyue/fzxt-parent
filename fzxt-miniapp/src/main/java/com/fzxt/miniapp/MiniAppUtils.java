package com.fzxt.miniapp;

import me.chanjar.weixin.common.util.crypto.SHA1;

public class MiniAppUtils {



    public static boolean checkSignature(String token,String timestamp, String nonce, String signature) {
        try {
            return SHA1.gen(new String[]{token, timestamp, nonce}).equals(signature);
        } catch (Exception var5) {
            return false;
        }
    }
}
