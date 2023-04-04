package com.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.domain.Employee;
import com.common.Result;
import com.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    /**
     * 员工登录
     * @param request
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request,@RequestBody Employee emp){

        Employee employee = service.isexistByUsername(emp.getUsername());
        String password = DigestUtils.md5DigestAsHex(emp.getPassword().getBytes());
        if (service.isexistByUsername(emp.getUsername())==null)
            return Result.error("该用户不存在");

        if (!(employee.getPassword().equals(password)))
            return Result.error("密码错误");

        if(employee.getStatus()!=1)
            return Result.error("该账号已被封禁");

        request.getSession().setAttribute("employee",employee.getId());
        return Result.success(employee);

    }

    /**
     * 员工登出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> login(HttpServletRequest request){

        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 员工列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<IPage<Employee>> page(@RequestParam("page") int page,@RequestParam("pageSize") int pageSize,String name){
        IPage page1=new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper =new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        service.page(page1, queryWrapper);
        return Result.success(page1);
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @PostMapping
    public Result<Boolean> insert(HttpServletRequest request,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        boolean save = service.save(employee);

        if (save)
            return Result.success(save);
        else
            return Result.error("数据异常");
    }

    /**
     * 编辑与修改状态
     * @param request
     * @param emp
     * @return
     */
    @PutMapping
    public Result<Boolean> ChangeStatus(HttpServletRequest request, @RequestBody Employee emp){

//        emp.setUpdateUser((Long)request.getSession().getAttribute("employee"));
//        emp.setUpdateTime(LocalDateTime.now());
        if(service.updateById(emp))
            return Result.success(service.updateById(emp));
        else{
            return Result.error("数据异常");
        }
//        return Result.success( service.ChangeStatus(emp));
    }

    /**
     * 根据id查找员工
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> Changeall(@PathVariable Long id)
    {

        Employee emp = service.getById(id);
        if(emp==null){
            return  Result.error("未知错误");
        }
        return  Result.success(emp);
    }
}
