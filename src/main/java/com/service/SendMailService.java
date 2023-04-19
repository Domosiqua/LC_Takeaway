package com.service;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
public interface SendMailService {


    void SendMail(String to);
    boolean checkcode(String to,String code);
    void deleteCode(String to);

}
