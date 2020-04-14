package com.fzxt.utils;

import java.util.UUID;

public class IDUtils {

    /**
     * 获取UUID
     * @return
     */
    public static String getPramaryId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) {
        System.out.println(getPramaryId());
    }
}


