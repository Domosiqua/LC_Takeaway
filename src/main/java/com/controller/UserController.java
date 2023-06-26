package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.Result;
import com.domain.User;
import com.service.SendMailService;
import com.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private SendMailService sendMailService;


    /**
     * 发送验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user){
        sendMailService.SendMail(user.getPhone());

        return Result.success("6");
    }

    /**
     * 用户登陆*检查验证码*
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody HashMap<String,String> map, HttpServletRequest request ){
        if(sendMailService.checkcode(map.get("phone"), map.get("code"))){
            LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
            User one = service.getOne(wrapper);
            if(one ==null){
                one=new User();
                one.setPhone(map.get("phone"));
                one.setStatus(1);
                service.save(one);
                one = service.getOne(wrapper);
            }
            request.getSession().setAttribute("user",one.getId());
            sendMailService.deleteCode(map.get("phone"));
            return Result.success(one);
        }else{
            return Result.error("验证码错误");
        }
    }

    /**
     * 用户登出
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    public Result<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return Result.success("退出成功");

    }
}
