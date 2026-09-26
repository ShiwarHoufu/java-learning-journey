package com.uestc.exception;

import com.uestc.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有异常
     */
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("程序出错了", e);
        return Result.error("程序出错了, 请联系管理员");
    }

    /**
     * 处理唯一约束冲突异常，例如部门名称重复
     * MySQL 唯一约束报错信息形如：Duplicate entry '研发部' for key 'dept.idx_dept_name'
     */
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("数据重复", e);

        // 取到具体重复的值，拼成 "xxxx已存在" 提示给前端
        String message = e.getMessage();
        int start = message.indexOf("'");
        int end = message.indexOf("'", start + 1);
        String value = message.substring(start + 1, end);

        return Result.error(value + "已存在");
    }
}
