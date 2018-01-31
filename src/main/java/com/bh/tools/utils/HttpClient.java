package com.bh.tools.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简易的Http请求客户端
 *
 * @author JanChao .
 */
public class HttpClient {

    private static final int CONNECT_TIME_OUT = 30000;
    private static final int CONNECTION_REQUEST_TIME_OUT = 10000;
    private static final int SOCKET_TIME_OUT = 10000;

    private static final int TRANS_TYPE_URL = 1;
    private static final int TRANS_TYPE_FORM_DATA = 2;
    private static final int TRANS_TYPE_FORM_JSON = 3;
    private static final int TRANS_TYPE_FORM_URL = 4;

    private String url;
    private int connectionRequestTimeout = CONNECTION_REQUEST_TIME_OUT;
    private int connectTimeout = CONNECT_TIME_OUT;
    private int socketTimeout = SOCKET_TIME_OUT;
    private int transType;


    private CloseableHttpClient closeableHttpClient;

    private RequestConfig requestConfig;

    private HttpClient() {

    }

    public HttpClient create() {
        return new HttpClient();
    }

    public CloseableHttpResponse post(Map<String, ?> params) {
        buildHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            HttpEntity entity = null;
            switch (transType) {
                case TRANS_TYPE_FORM_URL:
                    List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
                    for (String key : params.keySet()) {
                        urlParams.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
                    }
                    entity = new UrlEncodedFormEntity(urlParams, "UTF-8");
                    break;
                case TRANS_TYPE_FORM_JSON:
                    //TODO
                default:

            }
            httpPost.setEntity(entity);
            closeableHttpClient.execute(httpPost);
        } catch (IOException e) {
            //TODO
        }
        return null;
    }

    public CloseableHttpResponse get() {
        return null;
    }

    private void buildHttpClient() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        closeableHttpClient = httpClientBuilder.build();

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        requestConfigBuilder.setSocketTimeout(socketTimeout);
        requestConfigBuilder.setConnectTimeout(connectTimeout);
        requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
        requestConfig = RequestConfig.custom().build();

    }

    public String getUrl() {
        return url;
    }

    public HttpClient setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public HttpClient setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpClient setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public HttpClient setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public int getTransType() {
        return transType;
    }

    public HttpClient setTransType(int transType) {
        this.transType = transType;
        return this;
    }
}
