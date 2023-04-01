package com.common;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> ExceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        return Result.error("数据出错啦！");
    }
}
