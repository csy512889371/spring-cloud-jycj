package com.rjsoft.servicezuul.fallback;

import com.netflix.zuul.context.RequestContext;
import com.rjsoft.servicezuul.util.JsonUtil;
import com.rjsoft.servicezuul.util.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Zuul回退机制
 */
@Component
@Slf4j
public class ServiceConsumerFallbackProvider implements FallbackProvider {

    @Autowired
    private Environment env;

    @Override
    public String getRoute() {
        //*表示对所有服务进行回退操作，如需对具体某服务进行回退操作则需设置成eureka注册中心中的服务名
        return env.getProperty("zuulFallbackService", "*");
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {
                log.debug("Fallback close");
            }

            @Override
            public InputStream getBody() throws IOException {
                RequestContext context = RequestContext.getCurrentContext();
                Throwable throwable = context.getThrowable();
                if (!Objects.isNull(throwable)) {
                    log.error("", throwable.getCause());
                }
                Map<String, String> rsp = new HashMap<>();
                rsp.put("code", ResponseStatusEnum.SERVER_ERROR.getCode());
                rsp.put("message", env.getProperty(ResponseStatusEnum.SERVER_ERROR.getCode(), ResponseStatusEnum.SERVER_ERROR.getMessage()));
                return new ByteArrayInputStream(JsonUtil.beanToJsonStr(rsp).getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
                httpHeaders.setContentType(mediaType);
                return httpHeaders;
            }
        };
    }


}
