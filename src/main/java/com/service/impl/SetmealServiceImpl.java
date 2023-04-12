package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.DishFlavor;
import com.domain.Setmeal;
import com.domain.SetmealDish;
import com.dto.SetmealDto;
import com.service.SetmealDishService;
import com.service.SetmealService;
import com.mapper.SetmealMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author admin
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2023-04-10 09:19:56
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    @Transactional
    public boolean SaveWithDish(SetmealDto setmealDto) {
        boolean save = this.save(setmealDto);
        System.out.println(setmealDto.getSetmealDishes());
        for (SetmealDish s : setmealDto.getSetmealDishes()) {
            s.setSetmealId(setmealDto.getId().toString());
        }
        boolean b = setmealDishService.saveBatch(setmealDto.getSetmealDishes());

        return save&&b;
    }

    @Override

    public SetmealDto getOneWithDish(Long id) {
        SetmealDto setmealDto=new SetmealDto();
        Setmeal setmeal = this.getById(id);
        BeanUtils.copyProperties(setmeal,setmealDto);
        LambdaQueryWrapper<SetmealDish> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    @Override
    @Transactional
    public boolean UpdateWithDish(SetmealDto setmealDto) {
        boolean b1 = this.updateById(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId().toString());
        }

        LambdaQueryWrapper<SetmealDish> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,setmealDto.getId().toString());
        setmealDishService.remove(wrapper);
        setmealDishService.saveBatch(setmealDishes);
        return b1;

    }


    @Override
    @Transactional
    public void DeleteWithDish(ArrayList<Long> ids) {
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> querywrapper=new LambdaQueryWrapper<>();
        querywrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(querywrapper);
    }

    @Override
    public List<SetmealDto> GetlistWithDish(Long id) {
        LambdaUpdateWrapper<Setmeal> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(Setmeal::getCategoryId,id);
        wrapper.eq(Setmeal::getStatus,1);
        List<Setmeal> list = this.list(wrapper);
        List<SetmealDto> retlist = new ArrayList<>();
        for (Setmeal setmeal : list) {
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(setmeal,setmealDto);
            LambdaQueryWrapper<SetmealDish> qwrapper=new LambdaQueryWrapper<>();
            qwrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
            List<SetmealDish> list1 = setmealDishService.list(qwrapper);
            setmealDto.setSetmealDishes(list1);
            retlist.add(setmealDto);
        }
        return retlist;
    }
}




