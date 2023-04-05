package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.Result;
import com.domain.Category;
import com.domain.Dish;
import com.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Savepoint;
import java.util.List;

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

    /**
     * 分页展示菜品列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<IPage<Dish>> Page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
        IPage page1=new Page(page,pageSize);
        LambdaQueryWrapper<Dish> queryWrapper =new LambdaQueryWrapper();
        queryWrapper.orderByDesc(Dish::getSort);
        service.page(page1, queryWrapper);
        return Result.success(page1);
    }

    /**
     * 新增菜品
     * @param dish
     * @return
     */
    @PostMapping
    public Result<Boolean> Save(@RequestBody Dish dish){
        boolean save = service.save(dish);
        if (save){
            return Result.success(save);
        }else{
            return Result.error("数据异常请刷新页面后重试");
        }
    }

    /**
     * 根据菜品分类id查询所属菜品列表 服务套餐管理
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> List(Long categoryId){
        LambdaQueryWrapper<Dish> queryWrapper =new LambdaQueryWrapper();
        queryWrapper.eq(Dish::getCategoryId,categoryId);
        List<Dish> list = service.list(queryWrapper);
        return Result.success(list);
    }

    /**
     * 根据id修改售卖状态
     * @param dish
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<Boolean> UpdateStatus(Dish dish,@PathVariable Integer status,Long ids){
        dish.setStatus(status);
        dish.setId(ids);
        boolean b = service.updateById(dish);
        if (b){
            return Result.success(b);
        }else{
            return Result.error("数据异常请刷新页面后重试");
        }
    }

    @GetMapping("/{id}")
    public Result<Dish> GetOne(@PathVariable Long id){
        Dish dish = service.getById(id);
        return Result.success(dish);
    }



}
