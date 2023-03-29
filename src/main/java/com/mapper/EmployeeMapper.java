package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Employee;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
