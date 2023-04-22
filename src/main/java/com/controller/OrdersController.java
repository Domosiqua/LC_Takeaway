package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.CustomException;
import com.common.Result;
import com.domain.Orders;
import com.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService service;

    /**
     * 提交订单
     * @param orders
     * @return
     * @throws CustomException
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders) throws CustomException {
        service.submit(orders);
        return Result.success("提交成功");

    }

    /**
     * 获取当前用户订单
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public Result<Page<Orders>> page( int page,  int pageSize, String number){
        Page page1=new Page(page,pageSize);
        LambdaQueryWrapper<Orders> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(number!=null,Orders::getId,number);
        wrapper.orderByDesc(Orders::getOrderTime);
        service.page(page1,wrapper);
        return Result.success(page1);
    }

    /**
     * 获取全部订单 服务后台
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<Page<Orders>> Page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize,@RequestParam(value = "number",required = false)Long id,@RequestParam(value = "beginTime",required = false)String beginTimeStr,@RequestParam(value = "endTime",required = false)String endTimeStr){
        Page page1=new Page(page,pageSize);
        LambdaQueryWrapper<Orders> wrapper=new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Orders::getOrderTime);
        wrapper.like(id!=null,Orders::getId,id);
        LocalDateTime beginTime=null;
        LocalDateTime endTime=null;

        if (beginTimeStr!=null)
        {
            beginTime = LocalDateTime.parse(beginTimeStr,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println(beginTime);
        }
        if (endTimeStr!=null)
        {
            endTime = LocalDateTime.parse(endTimeStr,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println(endTime);
        }
        wrapper.between(beginTime!=null&&endTime!=null,Orders::getOrderTime,beginTime,endTime);
        service.page(page1,wrapper);
        return Result.success(page1);
    }
    @PutMapping()
    public Result<String> changeStatus(@RequestBody Orders orders){
        LambdaUpdateWrapper<Orders> wrapper=new LambdaUpdateWrapper();
        wrapper.eq(Orders::getId,orders.getId());
        wrapper.set(Orders::getStatus,orders.getStatus());
        boolean update = service.update(wrapper);
        if (update){
            return Result.success("修改成功");
        }else{
            return Result.error("数据异常，请刷新页面后重试");
        }
    }

}
