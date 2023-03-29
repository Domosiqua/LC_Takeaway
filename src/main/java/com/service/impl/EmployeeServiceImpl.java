package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Employee;
import com.mapper.EmployeeMapper;
import com.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {



}
