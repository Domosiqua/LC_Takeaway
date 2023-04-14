package com.service;

import com.common.CustomException;
import com.domain.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author admin
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2023-04-14 15:14:28
*/
public interface OrdersService extends IService<Orders> {

    void submit(Orders orders) throws CustomException;
}
