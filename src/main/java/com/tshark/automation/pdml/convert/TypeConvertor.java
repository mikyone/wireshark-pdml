package com.tshark.automation.pdml.convert;

import java.util.NoSuchElementException;

/**
 * Created by onicolas on 01/09/2015.
 */
public enum TypeConvertor {
    JSON_CONVERTOR("jsonConvertor"),
    DEFAULT_FIELD_CONVERTOR("defaultField"),
    HEX_CONVERTOR("hexConvertor"),
    DEFAULT_PROTOCOL_CONVERTOR("defaultProtocol");
    
    private final String type;


    TypeConvertor(String type) {
        this.type = type;
    }

    public static TypeConvertor getXmlType(String type){
        for(TypeConvertor expectedType : values()){
            if (expectedType.type.equals(type)){
                return expectedType;
            }
        }
        throw new NoSuchElementException(type+ " not found");
    }


    public Convertor getConvertor() {
        switch(this){
            case DEFAULT_FIELD_CONVERTOR: return DefaultFieldConvertor.get();
            case DEFAULT_PROTOCOL_CONVERTOR: return DefaultProtocolConvertor.get();
            case HEX_CONVERTOR: return HexFieldConvertor.get();
            case JSON_CONVERTOR: return JsonConvertor.get();
            default : break;
        }
        throw new NoSuchElementException(type+ " not found");
    }

    public String toString(){
        return type;
    }



}
