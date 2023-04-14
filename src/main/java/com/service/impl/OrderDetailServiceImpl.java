package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.OrderDetail;
import com.service.OrderDetailService;
import com.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2023-04-14 15:16:26
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




