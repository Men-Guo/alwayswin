package com.example.alwayswin.utils;

import org.apache.commons.lang.RandomStringUtils;


public class RandomStringUtil {
    /**
     * org.apache.commons.lang包下有一个RandomStringUtils类，
     * 其中有一个randomAlphanumeric(int length)函数，可以随机生成一个长度为length的字符串。
     * @param length
     * @return
     */
    public static String createRandomString(int length){
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
