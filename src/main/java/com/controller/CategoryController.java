package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.Result;
import com.domain.Category;
import com.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    /**
     * 数据列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<IPage<Category>> page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
        IPage page1=new Page(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper =new LambdaQueryWrapper();
        queryWrapper.orderByDesc(Category::getSort);
        service.page(page1, queryWrapper);
        return Result.success(page1);
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody Category category){
        boolean save=service.save(category);
        if (save){
            return Result.success(save);
        } else{
            return Result.error("数据异常");
        }
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PutMapping
    public Result<Boolean> Change(@RequestBody Category category){
        boolean b = service.updateById(category);
        if (b) {
            return Result.success(b);
        }else{
            return Result.error("数据错误");
        }
    }

    /**
     * 删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<Boolean> Delete(@RequestParam(name = "ids") Long ids){
        boolean b = service.removeById(ids);
        if (b) {
            return Result.success(b);
        }else{
            return Result.error("未知错误");
        }
    }

    /**
     * 根据type获取菜品或套餐列表
     * @param type
     * @return
     */
    @GetMapping("list")
    public Result<List<Category>> list(Integer type){

        LambdaQueryWrapper<Category> querywapper=new LambdaQueryWrapper<>();
        querywapper.eq(type!=null, Category::getType,type);
        querywapper.orderByDesc(Category::getSort);
        List<Category> list = service.list(querywapper);

        return Result.success(list);
    }
}
