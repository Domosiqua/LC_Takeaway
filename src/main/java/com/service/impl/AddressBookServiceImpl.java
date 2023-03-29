package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.AddressBook;
import com.mapper.AddressBookMapper;

import com.service.AddressBookService;
import org.springframework.stereotype.Service;


/**
* @author admin
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2023-03-29 17:15:09
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService {


}




