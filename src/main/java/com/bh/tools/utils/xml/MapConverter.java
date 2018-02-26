package com.bh.tools.utils.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * map to xml converter
 *
 * @author JanChao .
 */
public class MapConverter implements Converter {

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        AbstractMap map = (AbstractMap) source;
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            writer.startNode(entry.getKey().toString());
            writer.setValue(entry.getValue().toString());
            writer.endNode();
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Map<String, String> map = new HashMap<String, String>();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            map.put(reader.getNodeName(), reader.getValue());
            reader.moveUp();
        }
        return map;
    }

    public boolean canConvert(Class type) {
        return AbstractMap.class.isAssignableFrom(type);
    }
}
