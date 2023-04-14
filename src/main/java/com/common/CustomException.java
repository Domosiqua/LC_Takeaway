package com.common;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
/**
 * 业务异常
 */
public class CustomException extends Exception{
    public CustomException(String message) {
        super(message);
    }
}
