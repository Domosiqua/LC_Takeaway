package com.common;

import com.domain.CODE;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Data
public class Result <R>{
    private Integer code;
    private String msg;
    private R data;

    private Map map=new HashMap();

    public static <T> Result<T> success(T object){
        Result<T> r=new Result();
        r.data=object;
        r.code = CODE.SUCCESS;
        return r;
    }
    public static <T> Result<T> error(String msg){
        Result<T> r=new Result();
        r.code=0;
        r.msg=msg;
        return r;
    }
    public  Result<R> add(String key,String value){
        this.map.put(key,value);
        return this;
    }
}
