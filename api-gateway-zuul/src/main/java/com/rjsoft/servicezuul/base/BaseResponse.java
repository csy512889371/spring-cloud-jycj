package com.rjsoft.servicezuul.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应消息类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 回写属性
     */
    private Map<String, Object> properties = new HashMap<>();

    public BaseResponse addProperties(String key, Object value) {
        this.properties.put(key, value);
        return this;
    }
}
