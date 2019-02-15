package com.rjsoft.servicezuul.util;

/**
 * Created by Administrator on 2017/12/27.
 */
public enum ResponseStatusEnum {
    SUCCESS("success","成功"),
    PARAMETER_ERROR("parameter.error","参数错误"),
    PARAMETER_VALIDATION("parameter.validation","参数验证失败-{0}"),
    DATA_INPUT_ERROR("data.input.error","数据未输入"),
    DATA_CREATE_FAILURE("data.create.failure","新增数据失败"),
    DATA_QUERY_FAILURE("data.query.failure","查询数据失败"),
    DATA_UPDATE_FAILURE("data.update.failure","更新数据失败"),
    DATA_DELETE_FAILURE("data.delete.failure","删除数据失败"),
    FALL_BACK("fall.back","异常发生,进入fallback方法,接收的参数"),
    LIMIT_ERROR("limit.error","当前负载太高，请稍候重试"),
    LIMIT_IP("limit.ip","非法请求"),
    DEGRADE_SERVICE("degrade.service", "服务降级中"),
    TOKEN_TIMEOUT("token.timeout","token已过期，请重新登录"),
    NO_AUTH("no.auth","禁止访问"),
    NOT_FOUND("not.found","资源没找到"),
    SERVER_ERROR("server.error","系统完善中");


    private String code;
    private String message;

    ResponseStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
