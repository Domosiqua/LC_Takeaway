package com.controller;


import com.domain.CODE;
import com.domain.Employee;
import com.domain.Result;
import com.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

        Employee employee = service.isexist(emp.getUsername(),emp.getPassword());
        if (employee!=null){
            return Result.success(employee);
        }else{
            return Result.error("该用户不存在");
        }
    }
}
