package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    @Select("select * from employee where username=#{username} and password=MD5(#{password})")
    Employee isexits(String username,String password);
}
