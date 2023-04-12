package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.common.BaseContext;
import com.common.Result;
import com.domain.AddressBook;
import com.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService service;

    /**
     * 获取当前用户的地址信息
     * @return
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> getlist(){
        LambdaQueryWrapper<AddressBook> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        List<AddressBook> list = service.list(wrapper);
        return Result.success(list);
    }

    /**
     * 新增地址信息
     * @param addressBook
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        service.save(addressBook);
        return Result.success("新增成功");
    }
    /**
     * 修改默认地址
     */
    @PutMapping("/default")
    public Result<String> isDefault(@RequestBody AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        wrapper.set(AddressBook::getIsDefault,0);
        service.update(wrapper);
        addressBook.setIsDefault(1);
        boolean b = service.updateById(addressBook);
        if (b){
            return Result.success("修改成功");
        }else{
            return Result.error("网络异常请刷新后重试");
        }
    }

    /**
     * 修改地址
     * @param addressBook
     * @return
     */
    @PutMapping
    public Result<String> Update(@RequestBody AddressBook addressBook){
        boolean b = service.updateById(addressBook);
        if (b){
            return Result.success("修改成功");
        }else{
            return Result.error("网络异常请刷新后重试");
        }
    }

    /**
     * 根据ID找地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id){
        AddressBook byId = service.getById(id);
        if (byId!=null){
            return Result.success(byId);
        }else{
            return Result.error("未找到此对象");
        }
    }

    /**
     * 查询默认地址
     * @return
     */
    @GetMapping("/default")
    public Result<AddressBook> getAddressById(){
        LambdaUpdateWrapper<AddressBook> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getId,BaseContext.getCurrentId());
        wrapper.eq(AddressBook::getIsDefault,1);
        AddressBook one = service.getOne(wrapper);
        if (one!=null){
            return Result.success(one);
        }else{
            return Result.error("用户无默认地址");
        }
    }
}
