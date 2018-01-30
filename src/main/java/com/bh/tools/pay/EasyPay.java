package com.bh.tools.pay;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author JinYachao .
 */
public class EasyPay {

    public static void main(String[] args) {
        System.out.println("Hello EasyPay");
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //请求的配置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(3000)
                    .setConnectTimeout(3000)
                    .setConnectionRequestTimeout(3000)
                    .build();
            httpPost.setConfig(requestConfig);
            //请求的参数
            StringEntity stringEntity = new StringEntity("", "UTF-8");
            httpPost.setEntity(stringEntity);

            httpClient.execute(httpPost);
        } catch (Exception e) {

        }
    }

}
