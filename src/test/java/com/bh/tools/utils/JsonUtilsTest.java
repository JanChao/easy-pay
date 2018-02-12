package com.bh.tools.utils;

import com.bh.tools.pay.wx.model.UnifiedOrderRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * @author JanChao .
 */
public class JsonUtilsTest {

    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    @Test
    public void toJson() throws Exception {
        UnifiedOrderRequest request = new UnifiedOrderRequest();
        logger.debug("test");
        request.setAppId("123");
        request.setMchId("123");
        request.setDeviceInfo("123");
        String result = JsonUtils.toJSONString(request);
        logger.debug("toJson result:" + result);
        UnifiedOrderRequest request1 = JsonUtils.fromJSONString(result, UnifiedOrderRequest.class);
        logger.debug(String.format("appId:%s,mchId:%s,deviceInfo:%s", request1.getAppId(), request1.getMchId(), request1.getDeviceInfo()));
    }
}