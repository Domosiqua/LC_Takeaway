package com.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Category;
import com.mapper.CategoryMapper;
import com.service.CategoryService;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-04-04 15:52:41
*/
@Service
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService          {



}




