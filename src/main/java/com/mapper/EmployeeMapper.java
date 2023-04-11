package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

//    @Select("select * from employee where username=#{username}")
//    Employee isexitsByUsername(String username);
//    @Update("update employee set status=#{status} where id=#{id}")
//    boolean ChangeStatus(Integer status,Long id);

}
