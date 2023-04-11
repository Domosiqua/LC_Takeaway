package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.domain.Employee;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
public interface EmployeeService extends IService<Employee> {
//    Employee isexist(String username,String password);
    Employee isexistByUsername(String username);
}
