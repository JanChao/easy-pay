package com.bh.tools.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author JanChao .
 */
public class XMLUtils {

    private final static ConcurrentMap<String, JAXBContext> JAXB_CONTEXT_MAP = new ConcurrentHashMap<String, JAXBContext>();

    /**
     * 使用JDK自带的工具转化object为xml,此处需要被转换的object 使用注解
     *
     * @param c      要转换的对象类型
     * @param object 要转换的对象
     * @param <T>    类型
     * @return xml 字符串
     */
    public static <T> String toXml(Class<T> c, T object) throws JAXBException {

        StringWriter writer = new StringWriter();

        JAXBContext context = JAXB_CONTEXT_MAP.putIfAbsent(c.getSimpleName(), JAXBContext.newInstance(c));

        if (context == null) {
            context = JAXB_CONTEXT_MAP.get(c.getSimpleName());
        }
        Marshaller marshaller = context.createMarshaller();
        //编码格式
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        //是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //是否省略xml头信息（<?xml version="1.0" encoding="gb2312" standalone="yes"?>）
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);

        marshaller.marshal(object, writer);
        return writer.toString();
    }

}
