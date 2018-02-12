package com.bh.tools.http;

import com.bh.tools.http.model.Response;
import com.bh.tools.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简易的Http请求客户端
 *
 * @author JanChao .
 */
public class HttpClient {

    public static final int TRANS_TYPE_URL = 1;
    private static final int CONNECT_TIME_OUT = 30000;
    private static final int CONNECTION_REQUEST_TIME_OUT = 10000;
    private static final int SOCKET_TIME_OUT = 10000;
    public static final int TRANS_TYPE_FORM_DATA = 2;
    public static final int TRANS_TYPE_FORM_JSON = 3;
    public static final int TRANS_TYPE_FORM_URL = 4;
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private String url;
    private int connectionRequestTimeout = CONNECTION_REQUEST_TIME_OUT;
    private int connectTimeout = CONNECT_TIME_OUT;
    private int socketTimeout = SOCKET_TIME_OUT;
    private int transType;


    private CloseableHttpClient closeableHttpClient;

    private RequestConfig requestConfig;

    private HttpClient() {

    }

    public static HttpClient create() {
        return new HttpClient();
    }

    public Response post(Map<String, ?> params) {
        try {
            //初始化 closeableHttpClient、requestConfig
            buildHttpClient();
            //构造请求入参
            HttpEntity entity = buildRequestParams(params, transType);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(entity);
            return closeableHttpClient.execute(httpPost, new HttpResponseHandler());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response get() {
        return null;
    }

    private void buildHttpClient() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        closeableHttpClient = httpClientBuilder.build();

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        requestConfigBuilder.setSocketTimeout(socketTimeout);
        requestConfigBuilder.setConnectTimeout(connectTimeout);
        requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
        requestConfig = requestConfigBuilder.build();
    }

    /**
     * 根据指定传参类型transType组装请求参数
     *
     * @param params    请求入参
     * @param transType 传参类型
     * @return 请求实体 entity
     * @throws UnsupportedEncodingException 不支持的编码方式
     * @throws JsonProcessingException      序列化异常
     */
    private HttpEntity buildRequestParams(Map<String, ?> params, int transType) throws UnsupportedEncodingException, JsonProcessingException {
        HttpEntity entity = null;
        switch (transType) {
            case TRANS_TYPE_FORM_URL:
                List<NameValuePair> formUrlParams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, ?> entry : params.entrySet()) {
                    formUrlParams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                entity = new UrlEncodedFormEntity(formUrlParams, "UTF-8");
                break;
            case TRANS_TYPE_FORM_JSON:
                entity = new StringEntity(JsonUtils.toJSONString(params), "UTF-8");
                break;
            case TRANS_TYPE_FORM_DATA:
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                for (String key : params.keySet()) {
                    Object o = params.get(key);
                    if (o instanceof File) {
                        FileBody body = new FileBody((File) o);
                        builder.addPart(key, body);
                    } else {
                        builder.addTextBody(key, String.valueOf(o));
                    }
                }
                entity = builder.build();
                break;
            default:
                List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, ?> entry : params.entrySet()) {
                    urlParams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                setUrl(getUrl() + "?" + URLEncodedUtils.format(urlParams, "UTF-8"));
                break;
        }
        return entity;
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
