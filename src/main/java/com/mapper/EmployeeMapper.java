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
//    @Select("select * from employee where username=#{username} and password=MD5(#{password})")
//    Employee isexits(String username,String password);

    @Select("select * from employee where username=#{username}")
    Employee isexitsByUsername(String username);
//    @Select("select status from employee where username=#{username} and password=MD5(#{password})")
//    int isStatus(String username,String password);
}
