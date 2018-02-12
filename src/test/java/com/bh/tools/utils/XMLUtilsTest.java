package com.bh.tools.utils;

import com.bh.tools.pay.wx.model.UnifiedOrderRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;

/**
 * @author JanChao .
 */
public class XMLUtilsTest {

    private static final Logger logger = LogManager.getLogger();

    @Before
    public void setUp() {
    }

    @Test
    public void toXml() {
        UnifiedOrderRequest request = new UnifiedOrderRequest();
        logger.debug("test");
        request.setAppId("123");
        request.setMchId("123");
        request.setDeviceInfo("123");
        try {
            String result = XMLUtils.toXml(UnifiedOrderRequest.class, request);
            logger.debug(result);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("xml", UnifiedOrderRequest.class);
        System.out.println(xStream.toXML(request));
    }
}