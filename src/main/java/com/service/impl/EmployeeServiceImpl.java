package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Employee;
import com.mapper.EmployeeMapper;
import com.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Autowired
    private EmployeeMapper mapper;
//    @Override
//    public Employee isexist(String username, String password) {
//
//        return mapper.isexits(username,password);
//
//    }

    @Override
    public Employee isexistByUsername(String username) {
      return mapper.isexitsByUsername(username);

    }

    @Override
    public boolean ChangeStatus(Employee emp) {
       return mapper.ChangeStatus(emp.getStatus(),emp.getId());
    }


}
