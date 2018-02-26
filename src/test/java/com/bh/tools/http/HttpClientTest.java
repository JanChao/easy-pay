package com.bh.tools.http;

import com.bh.tools.utils.JsonUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JanChao .
 */
public class HttpClientTest {
    private static String host = "xxx";

    @Test
    public void post() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("a", "123");
        HttpClient httpClient = HttpClient.create()
                .setUrl(host + "xxx")
                .setRequestParams(params)
                .setTransType(HttpClient.TRANS_TYPE_FORM_DATA);
        System.out.println(JsonUtils.toJSONString(httpClient.post()));
    }
}