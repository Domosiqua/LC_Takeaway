package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Dish;
import com.service.DishService;
import com.mapper.DishMapper;
import com.service.DishService;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-04-05 12:58:39
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService {

}




