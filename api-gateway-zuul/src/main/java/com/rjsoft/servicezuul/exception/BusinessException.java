package com.rjsoft.servicezuul.exception;

import lombok.Data;

/**
 * 业务异常
 */
@Data
public class BusinessException extends RuntimeException {
    
    //所属模块
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String message;


    public BusinessException(String module, String code, Object[] args, String message) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.message = message;
    }

    public BusinessException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BusinessException(String module, String message) {
        this(module, null, null, message);
    }

    public BusinessException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public BusinessException(String message) {
        this(null, null, null, message);
    }

    @Override
    public String toString() {
        return this.getClass() + "{" +
                "module='" + module + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
