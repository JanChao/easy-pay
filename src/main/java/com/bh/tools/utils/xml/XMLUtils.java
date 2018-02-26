package com.bh.tools.utils.xml;

import com.thoughtworks.xstream.XStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author JanChao .
 */
public class XMLUtils {

    private final static ConcurrentMap<String, JAXBContext> JAXB_CONTEXT_MAP = new ConcurrentHashMap<String, JAXBContext>();

    /**
     * 使用JDK自带的工具转化Javabean为xml,此处需要被转换的Javabean 使用注解
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

    public static Object fromXml(Class c, String xmlString) throws JAXBException {
        JAXBContext context = JAXB_CONTEXT_MAP.putIfAbsent(c.getSimpleName(), JAXBContext.newInstance(c));
        if (context == null) {
            context = JAXB_CONTEXT_MAP.get(c.getSimpleName());
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(xmlString));
    }

    /**
     * 使用XStream序列化XML
     *
     * @param c            被转换的对象类型
     * @param object       被转换的对象
     * @param useRootAlias 是否使用根节点别名
     * @param rootAlias    根节点别名
     * @return 序列化后的字符串
     */
    public static <T> String toXml(Class<T> c, T object, boolean useRootAlias, String rootAlias) {
        XStream xStream = new XStream();
        //注册Ma转换器用于转换Map对象
        xStream.registerConverter(new MapConverter());
        if (useRootAlias) {
            xStream.alias(rootAlias, c);
        }
        return xStream.toXML(object);
    }

    /**
     * 使用XStream反序列化XML
     * 待反序列化的字符串根节点如果为xml则需指明别名代表的对象类型不然会抛异常
     *
     * @param c            反序列化的对象类型
     * @param xmlString    xml字符串
     * @param useRootAlias 是否使用根节点别名
     * @param rootAlias    根节点别名
     * @return 反序列化结果
     */
    public static Object fromXml(Class c, String xmlString, boolean useRootAlias, String rootAlias) {
        XStream xStream = new XStream();
        xStream.registerConverter(new MapConverter());
        if (useRootAlias) {
            xStream.alias(rootAlias, c);
        }
        return xStream.fromXML(xmlString);
    }
}
