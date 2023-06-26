package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.common.BaseContext;
import com.common.Result;
import com.domain.ShoppingCart;
import com.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public Result<List<ShoppingCart>> Getlist(){
        LambdaUpdateWrapper<ShoppingCart> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartService.list(wrapper);
        return Result.success(list);
    }

    /**
     * 添加菜品或者套餐
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public Result<String> Add(@RequestBody ShoppingCart shoppingCart){
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        if (shoppingCart.getDishId()!=null){
            LambdaQueryWrapper<ShoppingCart> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
            ShoppingCart one = shoppingCartService.getOne(wrapper);
            if (one!=null){
                one.setNumber(one.getNumber()+1);
                shoppingCartService.updateById(one);
            }else{
                shoppingCart.setNumber(1);
                shoppingCartService.save(shoppingCart);
            }
        }else{
            LambdaQueryWrapper<ShoppingCart> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            ShoppingCart one = shoppingCartService.getOne(wrapper);
            if(one!=null){
                one.setNumber(one.getNumber()+1);
                shoppingCartService.updateById(one);
            }else{
                shoppingCart.setNumber(1);
                shoppingCartService.save(shoppingCart);
            }
        }
        return Result.success("OJBK");
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public Result<String> claen(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,currentId);
        shoppingCartService.remove(wrapper);
        return Result.success("清除成功");
    }

    /**
     * 删除菜品或套餐
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public Result<String> Sub(@RequestBody ShoppingCart shoppingCart){
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        if (shoppingCart.getDishId()!=null){
            LambdaQueryWrapper<ShoppingCart> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
            wrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
            ShoppingCart one = shoppingCartService.getOne(wrapper);
            if (one.getNumber()>1){
                one.setNumber(one.getNumber()-1);
                shoppingCartService.updateById(one);
            }else{
                shoppingCartService.removeById(one.getId());
            }
        }else{
            LambdaQueryWrapper<ShoppingCart> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            wrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
            ShoppingCart one = shoppingCartService.getOne(wrapper);
            if(one.getNumber()>1){
                one.setNumber(one.getNumber()-1);
                shoppingCartService.updateById(one);
            }else{
                shoppingCartService.removeById(one.getId());
            }
        }
        return Result.success("OJBK");
    }

}
