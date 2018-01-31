package com.bh.tools.pay;

import com.bh.tools.pay.wx.model.UnifiedOrderRequest;
import com.bh.tools.utils.XmlUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * @author JinYachao .
 */
public class EasyPay {

    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        System.out.println("Hello EasyPay");
        try {
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) {
                }

                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
            CloseableHttpClient httpClient = httpClientBuilder.build();

            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //请求的配置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(3000)
                    .setConnectTimeout(3000)
                    .setConnectionRequestTimeout(3000)
                    .build();
            httpPost.setConfig(requestConfig);
            UnifiedOrderRequest request = new UnifiedOrderRequest();
            //请求的参数
            StringEntity stringEntity = new StringEntity(XmlUtil.toXml(UnifiedOrderRequest.class, request), "UTF-8");
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            logger.debug(response.getStatusLine());
            logger.debug(EntityUtils.toString(response.getEntity(), "UTF-8"));
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
