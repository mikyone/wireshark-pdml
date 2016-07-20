package com.tshark.automation.pdml.parser;

import java.util.NoSuchElementException;

/**
 * Created by onicolas on 31/08/2015.
 */
public enum TypePdmlXmlElement {
    FIELD("field"),
    PROTOCOL("protocol");

    private String type;

    TypePdmlXmlElement(String type) {
        this.type = type;
    }

    public static TypePdmlXmlElement getXmlType(String type){
        for(TypePdmlXmlElement expectedType : values()){
            if (expectedType.type.equals(type)){
                return expectedType;
            }
        }
        throw new NoSuchElementException(type+ " not found");
    }

}
