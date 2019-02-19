package com.rjsoft.servicezuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.rjsoft.servicezuul.util.JsonUtil;
import com.rjsoft.servicezuul.util.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class DegradeFilter extends ZuulFilter {
    @Autowired
    private Environment env;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        Object success = context.get("isSuccess");
        return success == null ? true : Boolean.parseBoolean(success.toString());
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String serviceId = (String) context.get("serviceId");
        String degradeService = env.getProperty("degradeService");
        if (StringUtils.isEmpty(degradeService)) {
            return null;
        } else {
            List<String> degradeServices = Arrays.asList(degradeService.split(","));
            if (degradeServices.contains(serviceId)) {
                try {
                    context.set("isSuccess", false);
                    context.setSendZuulResponse(false);
                    Map<String, String> rsp = new HashMap<>();
                    rsp.put("code", ResponseStatusEnum.DEGRADE_SERVICE.getCode());
                    rsp.put("message", env.getProperty(ResponseStatusEnum.DEGRADE_SERVICE.getCode(), ResponseStatusEnum.DEGRADE_SERVICE.getMessage()));
                    context.setResponseBody(JsonUtil.beanToJsonStr(rsp));
                    context.getResponse().setContentType("application/json; charset=utf-8");
                    return null;
                } catch (JsonProcessingException e) {
                    log.error("Filter error {}", e.getMessage());
                    context.set("isSuccess", false);
                    context.setSendZuulResponse(false);
                    return null;
                }
            }
        }
        return null;
    }
}
