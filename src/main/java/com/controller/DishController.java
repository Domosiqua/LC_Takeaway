package com.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.Result;
import com.domain.Category;
import com.domain.Dish;
import com.domain.DishFlavor;
import com.dto.DishDto;
import com.service.CategoryService;
import com.service.DishFlavorService;
import com.service.DishService;
import com.sun.org.apache.bcel.internal.generic.LADD;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})

@RestController
@RequestMapping("dish")
public class DishController {
    @Autowired
    private DishService service;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页展示菜品列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<IPage<DishDto>> Page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize,String name){
        IPage<DishDto> page2=new Page();
        IPage<Dish> page1=new Page(page,pageSize);
        LambdaQueryWrapper<Dish> queryWrapper =new LambdaQueryWrapper();
        queryWrapper.orderByDesc(Dish::getSort);
        queryWrapper.like(name!=null,Dish::getName,name);
        service.page(page1, queryWrapper);
        BeanUtils.copyProperties(page1,page2,"records");

        List<Dish> records = page1.getRecords();
        List<DishDto> list=  records.stream().map((item) ->{

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            String ca = categoryService.getById(categoryId).getName();
            dishDto.setCategoryName(ca);
            return dishDto;
        }).collect(Collectors.toList());



        page2.setRecords(list);
        return Result.success(page2);
    }

    /**
     * 新增菜品
     * @param dish
     * @return
     */
    @PostMapping
    public Result<String> Save(@RequestBody DishDto dishDto){


        service.saveWithFlavor(dishDto);
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        return Result.success("新增菜品成功");
    }

    /**
     * 根据菜品分类id或者菜品名称查询菜品以及口味 服务套餐管理与用户界面展示
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<DishDto>> List(Dish dish){
        List<DishDto> retlist=null;
        String key="dish_"+dish.getCategoryId()+"_"+dish.getStatus();
        retlist = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if (retlist!=null){
            return Result.success(retlist);
        }


        LambdaQueryWrapper<Dish> queryWrapper =new LambdaQueryWrapper();
        queryWrapper.eq(dish.getCategoryId()!=null, Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.like(dish.getName()!=null,Dish::getName,dish.getName());
        queryWrapper.eq(Dish::getStatus,1);
        List<Dish> list = service.list(queryWrapper);
        retlist=  list.stream().map((item) ->{
            Long id = item.getId();

            DishDto dishDto = service.getOneWithFlavor(id);

            BeanUtils.copyProperties(item,dishDto);
            return dishDto;
        }).collect(Collectors.toList());
        redisTemplate.opsForValue().set(key,retlist,60, TimeUnit.MINUTES) ;
        return Result.success(retlist);
    }

    /**
     * 根据id修改售卖状态
     * @param dish
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<Boolean> UpdateStatus(@PathVariable Integer status,Long[] ids){
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        ArrayList<Dish> list=new ArrayList<>();
        for (Long id : ids) {
            Dish dish=new Dish();
            dish.setId(id);
            dish.setStatus(status);
            list.add(dish);
        }
        boolean b = service.updateBatchById(list);
        if (b){
            return Result.success(b);
        }else{
            return Result.error("数据异常请刷新页面后重试");
        }
    }

    /**
     * 修改时数据回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDto> GetOne(@PathVariable Long id){
        DishDto dishDto = service.getOneWithFlavor(id);
        return Result.success(dishDto);
        
    }
    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result<String> Update(@RequestBody DishDto dishDto){
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
            boolean b = service.updateWithFlavor(dishDto);
            if (b){
                return Result.success("修改成功");
            }
            return Result.error("修改失败 请刷新页面后重试");
    }
    /**
     * 批量删除或单个删除
     * @return
     */
    @DeleteMapping
    public Result<String> DeleteByids(Long[] ids){
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        ArrayList<Long> list=new ArrayList();
        for (Long id : ids) {
            list.add(id);
        }
        service.DeleteWithFlavor(list);
        return Result.success("删除成功");
    }


}
