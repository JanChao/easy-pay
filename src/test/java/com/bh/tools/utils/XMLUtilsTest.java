package com.bh.tools.utils;

import com.bh.tools.pay.wx.model.UnifiedOrderRequest;
import com.bh.tools.utils.xml.XMLUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JanChao .
 */
public class XMLUtilsTest {

    @Before
    public void setUp() {
    }

    @Test
    public void toXml() {
        UnifiedOrderRequest request = new UnifiedOrderRequest();
        request.setAppId("123");
        request.setMchId("123");
        request.setDeviceInfo("123");

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "JanChao");
        params.put("age", "18");
        params.put("alias", "大头");
        try {
            String resultJAXB = XMLUtils.toXml(UnifiedOrderRequest.class, request);
            System.out.println(resultJAXB);
            System.out.println(JsonUtils.toJSONString(XMLUtils.fromXml(UnifiedOrderRequest.class, resultJAXB)));

            String resultXStream = XMLUtils.toXml(Map.class, params, true, "xml");
            System.out.println(resultXStream);
            System.out.println(JsonUtils.toJSONString(XMLUtils.fromXml(Map.class, resultXStream, true, "xml")));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}