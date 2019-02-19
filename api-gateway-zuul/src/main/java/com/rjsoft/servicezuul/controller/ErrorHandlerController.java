package com.rjsoft.servicezuul.controller;


import com.rjsoft.servicezuul.base.BaseResponse;
import com.rjsoft.servicezuul.util.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 接口处理404、500等错误页面
 */
@RestController
@Slf4j
public class ErrorHandlerController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping("/error")
    public BaseResponse error(HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        Map<String, Object> errorAttributes = getErrorAttributes(request, true);
        String message = (String) errorAttributes.get("message");
        String trace = (String) errorAttributes.get("trace");
        if (!StringUtils.isEmpty(trace)) {
            message += String.format(" and trace %s", trace);
        }
        baseResponse.setCode(ResponseStatusEnum.SERVER_ERROR.getCode());
        baseResponse.setMessage(ResponseStatusEnum.SERVER_ERROR.getMessage());
        log.error(message);
        return baseResponse;
    }


    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest webRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }
}
