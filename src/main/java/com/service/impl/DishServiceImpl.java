package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Dish;
import com.domain.DishFlavor;
import com.dto.DishDto;
import com.service.DishFlavorService;
import com.service.DishService;
import com.mapper.DishMapper;
import com.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author admin
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-04-05 12:58:39
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService {

    @Autowired
    private DishFlavorService service;

    @Transactional
    @Override
    public boolean saveWithFlavor(DishDto dishDto) {
        boolean save = this.save(dishDto);
        Long dishId= dishDto.getId();//菜品id
        for (DishFlavor f: dishDto.getFlavors()) {
            f.setDishId(dishId);
        }
        boolean b = service.saveBatch(dishDto.getFlavors());
        return b&&save;

    }

    @Override
    @Transactional
    public boolean updateWithFlavor(DishDto dishDto) {
        boolean b = this.updateById(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setId(dishDto.getId());
        }
        LambdaQueryWrapper<DishFlavor> querywrapper=new LambdaQueryWrapper<>();
        querywrapper.eq(DishFlavor::getDishId,dishDto.getId());

        service.remove(querywrapper);

        service.saveBatch(flavors);
        return b;



    }

    @Override
    @Transactional
    public DishDto getOneWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = this.getById(id);
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWapper=new LambdaQueryWrapper<>();
        queryWapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = service.list(queryWapper);
        dishDto.setFlavors(list);
        return dishDto;

    }

    @Override
    @Transactional
    public void DeleteWithFlavor(ArrayList<Long> ids) {
        this.removeByIds(ids);
        LambdaQueryWrapper<DishFlavor> querywrapper=new LambdaQueryWrapper<>();
        querywrapper.in(DishFlavor::getDishId,ids);
        service.remove(querywrapper);
    }
}




