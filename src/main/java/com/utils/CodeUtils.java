package com.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Component
public class CodeUtils {
    private static String [] patch = {"000000","00000","0000","000","00","0",""};

    public String generaor(String tel){
        int hashCode = tel.hashCode();
        int enryption = 2023414;
        long result = hashCode ^ enryption;
        long nowtime =System.currentTimeMillis();
        result = Math.abs(result ^ nowtime);
        long code=result%1000000;
        String str=code+"";
        return patch[str.length()]+code;
    }
    @Cacheable(value = "code_",key = "#to")
    public String get(String to){

        return null;
    }

}
