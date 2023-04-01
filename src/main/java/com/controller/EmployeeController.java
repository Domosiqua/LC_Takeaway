package com.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.domain.Employee;
import com.common.Result;
import com.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result<IPage<Employee>> page(@RequestParam("page") int page,@RequestParam("pageSize") int pageSize){
        IPage page1=new Page(page,pageSize);
        IPage page2 = service.page(page1, null);
        return Result.success(page2);
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @PostMapping
    public Result<Boolean> insert(HttpServletRequest request,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        System.out.println(employee);
        boolean save = service.save(employee);

        if (save)
            return Result.success(save);
        else
            return Result.error("数据异常");
    }
}
