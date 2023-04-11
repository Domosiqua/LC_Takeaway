package com.service;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
public interface SendMailService {


    String SendMail(String to);
    boolean checkcode(String to,String code);

}
