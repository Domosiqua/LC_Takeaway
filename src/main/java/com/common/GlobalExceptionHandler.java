package com.common;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;

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
        if (ex.getMessage().contains("Duplicate entry")){
           log.error(ex.getMessage().split(" ")[2]+"已经存在");
            return Result.error(ex.getMessage().split(" ")[2]+"已经存在");
        }
        return Result.error("未知错误");
    }
}
