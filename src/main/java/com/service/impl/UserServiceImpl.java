package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.User;
import com.service.UserService;
import com.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2023-04-11 13:15:57
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




