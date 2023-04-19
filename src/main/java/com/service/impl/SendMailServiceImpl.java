package com.service.impl;

import com.service.SendMailService;
import com.utils.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Service
@Slf4j
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private CodeUtils codeUtils;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private RedisTemplate redisTemplate;
    //发送人
    private String from="2961777212@qq.com";
    //接受人

    //主题
    private String subject="[验证码]";
    //正文
//    private String context="测试内容";


    @Override
    public void SendMail(String to) {
        String code = codeUtils.generaor(to);
        for (int i = 0; i < 10; i++) {
            log.info("验证码为"+code);
        }
        SimpleMailMessage message= new SimpleMailMessage();
        message.setFrom(from+"(Domosiqua)");
        message.setTo(to);
        message.setSubject(subject);
        message.setText("您的验证码为"+code+",五分钟内有效请勿泄露给他人");
//        sender.send(message);// 取消注释的话就会发送邮件
        redisTemplate.opsForValue().set(to, code,5, TimeUnit.MINUTES);
        return ;
    }

    public boolean checkcode(String to,String code){
        Object rightcode = redisTemplate.opsForValue().get(to);
        return rightcode.equals(code);
    }

    @Override
    public void deleteCode(String to) {
        redisTemplate.delete(to);
    }

}
