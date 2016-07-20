package com.tshark.automation.pdml.parser.processor;

import com.tshark.automation.pdml.parser.PdmlXmlElement;
import com.tshark.automation.pdml.parser.Token;
import com.tshark.automation.pdml.parser.TypePdmlXmlElement;
import com.tshark.automation.pdml.type.Field;
import com.tshark.automation.pdml.type.Protocol;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* Created by onicolas on 31/08/2015.
*/
public class DefaultProcessField implements ProcessField<Token> {
    private final Map<String, PdmlXmlElement> fields;
    private final Map<String, PdmlXmlElement> protocols;


    public DefaultProcessField(List<PdmlXmlElement> xmlFields, List<PdmlXmlElement> xmlProtocols) {
        fields= buildMap(xmlFields, TypePdmlXmlElement.PROTOCOL);
        protocols = buildMap(xmlProtocols, TypePdmlXmlElement.FIELD);
    }

    private Map<String, PdmlXmlElement> buildMap(List<PdmlXmlElement> xmlElements, TypePdmlXmlElement type) {
        return xmlElements.stream().collect(Collectors.toMap(PdmlXmlElement::getName, xmlElt -> xmlElt));
    }

    @Override
    public Token processField(Field field) {
        PdmlXmlElement pdmlXmlElement = fields.get(field.getName());
        if (pdmlXmlElement == null){
            throw new RuntimeException(field.getName() + " is not there! It's a bug and it can't happen");
        }
        String convertedValue = pdmlXmlElement.getConvertor().convert(field);
        return Token.get(field.getName(), convertedValue);
    }

    @Override
    public Token processProtocol(Protocol protocol) {
        PdmlXmlElement pdmlXmlElement = protocols.get(protocol.getName());
        if (pdmlXmlElement == null){
            throw new RuntimeException(protocol.getName() + " is not there! It's a bug and it can't happen");
        }
        String convertedValue = pdmlXmlElement.getConvertor().convert(protocol);
        return Token.get(protocol.getName(), convertedValue);
    }

}
