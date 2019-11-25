package com.tjh.livy_rest_task.common.spring_template;

import com.tjh.livy_rest_task.common.config.HttpClientConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

@Component
public class HttpRestTemplate {
    @Autowired
    public HttpClientConfig httpClientConfig;

    @Bean(name = "httpClientConnectionManager")
    public HttpClientConnectionManager gethttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(httpClientConfig.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(httpClientConfig.getDefaultMaxPerRoute());
        return connectionManager;
    }

    @Bean(name = "httpRequestConfig")
    public RequestConfig getHttpRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(httpClientConfig.getConnectTimeout()).setConnectionRequestTimeout(httpClientConfig.getConnectionRequestTimeout()).setSocketTimeout(httpClientConfig.getSocketTimeout()).build();
    }

    @Bean(name = "httpClientBuild")
    public HttpClient getHttpClientBuild(@Qualifier("httpClientConnectionManager") HttpClientConnectionManager manager,@Qualifier("httpRequestConfig") RequestConfig requestConfig) {
        return HttpClientBuilder.create().setConnectionManager(manager).setDefaultRequestConfig(requestConfig).build();
    }

    @Bean(name = "httpRequestFactory")
    public ClientHttpRequestFactory getHttpRequestFactory(@Qualifier("httpClientBuild") HttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean(name = "httpRestTemplateObj")
    public RestTemplate getHttpRestTemplate(@Qualifier("httpRequestFactory") ClientHttpRequestFactory requestFactory) {
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        List<HttpMessageConverter<?>> msgList = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> msg : msgList) {
            if (msg instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) msg).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }

        return restTemplate;
    }
}
