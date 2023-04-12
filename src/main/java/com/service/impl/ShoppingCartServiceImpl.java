package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.ShoppingCart;
import com.service.ShoppingCartService;
import com.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2023-04-12 16:29:37
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




