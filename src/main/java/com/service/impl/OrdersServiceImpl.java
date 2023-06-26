package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.BaseContext;
import com.common.CustomException;
import com.domain.*;
import com.service.*;
import com.mapper.OrdersMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @author admin
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2023-04-14 15:14:28
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void submit(Orders orders) throws CustomException {
        Long userid= BaseContext.getCurrentId();
        User user = userService.getById(userid);
        LambdaQueryWrapper<ShoppingCart> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userid);//查询当前用户购物车数据以完善订单数据
        List<ShoppingCart> list = shoppingCartService.list(wrapper);
        if (list==null||list.size()==0){
            throw new CustomException("购物车为空 不能下单");
        }
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook==null){
            throw new CustomException("地址有误 无法下单");
        }
        AtomicInteger amount=new AtomicInteger(0);
        Long orderId= IdWorker.getId();
        orders.setId(orderId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserId(userid);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getPhone());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());
        List<OrderDetail> OrderDetails=
        list.stream().map((item)->{
            OrderDetail orderDetail=new OrderDetail();

            BeanUtils.copyProperties(item,orderDetail);

            orderDetail.setOrderId(orderId);
            orderDetail.setId(null);
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());
        orderDetailService.saveBatch(OrderDetails);
        orders.setAmount(new BigDecimal(amount.get()));
        this.save(orders);


        //清空购物车
        shoppingCartService.remove(wrapper);

    }
}




