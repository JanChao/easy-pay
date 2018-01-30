package com.bh.tools.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author JanChao .
 */
public class XmlUtil {

    /**
     * 使用JDK自带的工具转化javabean为xml
     * 此处需要被转换的javabean 使用注解
     *
     * @param c 要转换的类
     * @return Marshaller
     */
    public static <T> Marshaller getMarshaller(Class<T> c) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(c);
        Marshaller marshaller = context.createMarshaller();
        //编码格式
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        //是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //是否省略xml头信息（<?xml version="1.0" encoding="gb2312" standalone="yes"?>）
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);

        return marshaller;
    }

}
