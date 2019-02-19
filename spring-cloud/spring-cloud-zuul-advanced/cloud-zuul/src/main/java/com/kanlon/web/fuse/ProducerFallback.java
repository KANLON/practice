package com.kanlon.web.fuse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 服务熔断例子
 *
 * @author zhangcanlong
 * @since 2019/2/19 15:17
 **/
@Component
public class ProducerFallback implements FallbackProvider {

    Logger logger = LoggerFactory.getLogger(ProducerFallback.class);

    /**
     * 指定处理的service
     **/
    @Override
    public String getRoute() {
        return "eureka-produce";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if(cause!=null && cause.getCause()!=null){
            String reason = cause.getCause().getMessage();
            logger.info("Exception {}",reason);
        }
        return fallbackResponse();
    }

    public ClientHttpResponse fallbackResponse(){
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "ok";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("The service is unavailable,".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
