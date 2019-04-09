package com.wzp.aiplatform.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    public static String encryptMD5(String str) {
        return DigestUtils.md5Hex(str);
    }
}