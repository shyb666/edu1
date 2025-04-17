package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }


    /**
     * 添加sql异常的处理机制
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
 //通过检测给出的异常信息是否含有 "Duplicate entry"（唯一约束中包含重复键值对）给出相应操作
        String message=ex.getMessage();
        if(message.contains("Duplicate entry"))
        {
            //名字信息在该异常的3号位置
            String[] split=message.split("   ");
            String name=split[2];
            String msg=name+ MessageConstant.ALREDY_EXIST;
            return Result.error(msg);
        }

        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }


}
