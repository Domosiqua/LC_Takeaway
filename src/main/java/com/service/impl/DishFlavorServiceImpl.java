package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.DishFlavor;
import com.service.DishFlavorService;
import com.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-04-05 14:41:16
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




