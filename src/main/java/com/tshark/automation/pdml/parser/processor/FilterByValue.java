package com.tshark.automation.pdml.parser.processor;

import com.tshark.automation.pdml.type.Field;
import com.tshark.automation.pdml.type.Protocol;

import java.util.Map;

/**
 * Created by onicolas on 31/08/2015.
 */
public class FilterByValue implements ProcessField<String> {
    private final Map<String, String> mapNameValue;


    public FilterByValue(Map<String, String> mapNameValue) {
        this.mapNameValue = mapNameValue;
    }

    @Override
    public String processField(Field field) {
        String value = mapNameValue.get(field.getName());
        return field.getShow().equals(value) ? field.getShow() : null;
    }

    @Override
    public String processProtocol(Protocol protocol) {
        String value = mapNameValue.get(protocol.getName());
        return protocol.getShowname().equals(value) ? protocol.getShowname() : null;

    }

}
