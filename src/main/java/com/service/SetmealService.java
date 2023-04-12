package com.service;

import com.domain.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dto.SetmealDto;

import java.util.ArrayList;
import java.util.List;

/**
* @author admin
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2023-04-10 09:19:56
*/
public interface SetmealService extends IService<Setmeal> {

    boolean SaveWithDish(SetmealDto setmealDto);

    SetmealDto getOneWithDish(Long id);

    boolean UpdateWithDish(SetmealDto setmealDto);

    void DeleteWithDish(ArrayList<Long> list);

    List<SetmealDto> GetlistWithDish(Long id);
}
