package com.service;

import com.domain.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dto.DishDto;

import java.util.ArrayList;
import java.util.List;

/**
* @author admin
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2023-04-05 12:58:39
*/
public interface DishService extends IService<Dish> {

    /**
     * 新增菜品  同时 插入菜品对应的口味 需操作两张表 DISH & DISHFLAVOR
     */
     boolean saveWithFlavor(DishDto dishDto);
     boolean updateWithFlavor(DishDto dishDto);
     DishDto getOneWithFlavor(Long id);
     void DeleteWithFlavor(ArrayList<Long> ids);
}
