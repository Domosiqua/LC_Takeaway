package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.Result;
import com.domain.Setmeal;
import com.dto.SetmealDto;
import com.service.CategoryService;
import com.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page<SetmealDto>> Page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, String name){
        Page<SetmealDto> page2 = new Page();
        Page<Setmeal> page1 = new Page(page,pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper =new LambdaQueryWrapper();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        setmealService.page(page1, queryWrapper);
        BeanUtils.copyProperties(page1,page2,"records");
        List<Setmeal> recods=page1.getRecords();

        List<SetmealDto> list=recods.stream().map((item)->{
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long CategoryId=item.getCategoryId();
            String ca = categoryService.getById(CategoryId).getName();
            setmealDto.setCategoryName(ca);
            return setmealDto;
        }).collect(Collectors.toList());

        page2.setRecords(list);
        return Result.success(page2);
    }

    /**
     * 新建套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(key = "setmealCache",allEntries = true)
    public Result<String> Save(@RequestBody SetmealDto setmealDto) {
        boolean b = setmealService.SaveWithDish(setmealDto);
        if (b){
            return Result.success("创建成功");
        }else{
            return Result.error("数据异常，请刷新页面后重试");
        }
    }

    /**
     * 修改套餐
     * @param setmealDto
     * @return
     */
    @PutMapping
    @CacheEvict(key = "setmealCache",allEntries = true)
    public Result<String> Update(@RequestBody SetmealDto setmealDto) {
        boolean b = setmealService.UpdateWithDish(setmealDto);
        if (b){
            return Result.success("修改成功");
        }else{
            return Result.error("数据异常，请刷新页面后重试");
        }
    }

    /**
     * 获取单个 用于数据回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealDto> GetOne(@PathVariable Long id){
        SetmealDto SetmealDto = setmealService.getOneWithDish(id);
        return Result.success(SetmealDto);
    }

    /**
     * 根据id获取全部套餐信息
     * @param id
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "'setmeal_'+#setmeal.categoryId+'_'+#setmeal.status")
    public Result<List<SetmealDto>> Getlist(Setmeal setmeal){

        List<SetmealDto> setmealDtos = setmealService.GetlistWithDish(setmeal.getCategoryId());

        return Result.success(setmealDtos);
    }
    /**
     * 根据id修改售卖状态
     * @param dish
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(key = "setmealCache",allEntries = true)
    public Result<Boolean> UpdateStatus(@PathVariable Integer status,Long[] ids){
        ArrayList<Setmeal> list=new ArrayList<>();
        for (Long id : ids) {
            Setmeal setmeal=new Setmeal();
            setmeal.setId(id);
            setmeal.setStatus(status);
            list.add(setmeal);
        }
        boolean b = setmealService.updateBatchById(list);
        if (b){
            return Result.success(b);
        }else{
            return Result.error("数据异常请刷新页面后重试");
        }
    }
    /**
     * 批量删除或单个删除
     * @return
     */
    @DeleteMapping
    @CacheEvict(key = "setmealCache",allEntries = true)
    public Result<String> DeleteByids(Long[] ids){
        LambdaQueryWrapper<Setmeal> wrapper=new LambdaQueryWrapper();
        wrapper.in(Setmeal::getId,ids);
        List<Setmeal> list1 = setmealService.list(wrapper);
        for (Setmeal setmeal : list1) {
            if (setmeal.getStatus()!=0)
                return Result.error("请停售后再删除");
        }
        ArrayList<Long> list=new ArrayList();
        for (Long id : ids) {
            list.add(id);
        }
        setmealService.DeleteWithDish(list);
        return Result.success("删除成功");
    }

}
