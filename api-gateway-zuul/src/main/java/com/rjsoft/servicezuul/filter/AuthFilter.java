package com.rjsoft.servicezuul.filter;


import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@Slf4j
public class AuthFilter extends ZuulFilter {
    @Autowired
    private Environment env;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        Object success = context.get("isSuccess");
        return success == null ? true : Boolean.parseBoolean(success.toString());
    }

    @Override
    public Object run() {
        /*
            1、判断uri是否属于白名单，如果是不需要用户认证，如果不是转2
            2、处理用户认证
                1)从SecurityContext对象中获取认证对象Authentication
                2）如果为空返回禁止访问信息
                3）如果不为空，把当前登录人的username设置到请求头信息中
        */
        if (processApiWhite()) {
            return null;
        } else {
            return proceessAuthentication();
        }

    }

    /**
     * 白名单
     *
     * @return
     */
    private Boolean processApiWhite() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String uri = request.getRequestURI();
        String apiWhite = env.getProperty("apiWhite");
        if (!StringUtils.isEmpty(apiWhite)) {
            List<String> whiteApis = Arrays.asList(apiWhite.split(","));
            for (String whiteApi : whiteApis) {
                Pattern pattern = Pattern.compile(whiteApi);
                Matcher m = pattern.matcher(uri);
                if (m.find()) {
                    return true;
                }
            }
            for (String whiteApi : whiteApis) {
                if (whiteApi.contains("{") && whiteApi.contains("}")) {
                    if (whiteApi.split("/").length == uri.split("/").length) {
                        String regex = whiteApi.replaceAll("\\{.*}", ".*{1,}");
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(uri);
                        if (matcher.find()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 用户认证
     *
     * @return
     */
    private Object proceessAuthentication() {
        RequestContext context = RequestContext.getCurrentContext();

        Authentication authentication = null;

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            String uri = context.getRequest().getRequestURI();
            if (uri.startsWith("/logout")) {
                new SecurityContextLogoutHandler().logout(context.getRequest(), context.getResponse(), authentication);
            }
            try {
                context.set("isSuccess", false);
                context.setSendZuulResponse(false);
                Map<String, String> rsp = new HashMap<>();
                rsp.put("code", ResponseStatusEnum.NO_AUTH.getCode());
                rsp.put("message", env.getProperty(ResponseStatusEnum.NO_AUTH.getCode(), ResponseStatusEnum.NO_AUTH.getMessage()));
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
        else if (!(authentication instanceof OAuth2Authentication)){
            try {
                context.set("isSuccess",false);
                context.setSendZuulResponse(false);
                Map<String,String> rsp = new HashMap<>();
                rsp.put("code", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
                rsp.put("message", Constants.NOTOKEN);
                context.setResponseBody(JsonUtil.beanToJsonStr(rsp));
                context.getResponse().setContentType("application/json; charset=utf-8");
                context.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
                return null;
            } catch (JsonProcessingException e) {
                log.error("Filter error {}",e.getMessage());
                context.set("isSuccess",false);
                context.setSendZuulResponse(false);
                return null;
            }
        }

        else {*/
        JSONObject jsonObject = JSONObject.parseObject(context.getRequest().getHeader("Authorization"));
        if (Objects.nonNull(jsonObject)) {
            String appSn = jsonObject.getString("appSn");
            if (!StringUtils.isEmpty(appSn)) {
                context.addZuulRequestHeader("currentAppSn", appSn);
            }
        }
        // TODO
        //context.addZuulRequestHeader("currentUsername",((CustomUserDetails)authentication.getPrincipal()).getUsername());
        return null;
        //}
    }
}
