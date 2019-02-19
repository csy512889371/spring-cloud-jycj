package com.rjsoft.servicezuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.rjsoft.servicezuul.util.JsonUtil;
import com.rjsoft.servicezuul.util.NetworkUtil;
import com.rjsoft.servicezuul.util.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IP限制过滤器
 */
@Component
@Slf4j
public class IPFilter extends ZuulFilter {

    @Autowired
    private Environment env;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    /**
     * 过滤器优先级，数字越大，优先级越低，其实就是过滤器链里面用到的
     */
    public int filterOrder() {
        return 1;
    }

    @Override
    /**
     * 是否执行该过滤器
     */
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        Object success = context.get("isSuccess");
        return success == null ? true : Boolean.parseBoolean(success.toString());
    }

    @Override
    /**
     * 过滤逻辑方法
     */
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String ip = NetworkUtil.getIpAddress(request);
        String ipStr = env.getProperty("ipBlack");
        if (StringUtils.isEmpty(ipStr) || StringUtils.isEmpty(ip)) {
            context.set("isSuccess", false);
            context.setSendZuulResponse(false);
            return null;
        } else {
            List<String> ipBlackList = Arrays.asList(env.getProperty("ipBlack").split(","));
            if (ipBlackList.contains(ip)) {
                try {
                    context.set("isSuccess", false);
                    context.setSendZuulResponse(false);
                    Map<String, String> rsp = new HashMap<>();
                    rsp.put("code", ResponseStatusEnum.LIMIT_IP.getCode());
                    rsp.put("message", env.getProperty(ResponseStatusEnum.LIMIT_IP.getCode(), ResponseStatusEnum.LIMIT_IP.getMessage()));
                    context.setResponseBody(JsonUtil.beanToJsonStr(rsp));
                    context.getResponse().setContentType("application/json; charset=utf-8");
                    return null;
                } catch (JsonProcessingException e) {
                    log.error("Filter error {}", e.getMessage());
                    context.set("isSuccess", false);
                    context.setSendZuulResponse(false);
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}
