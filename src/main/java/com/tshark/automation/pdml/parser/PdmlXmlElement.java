package com.tshark.automation.pdml.parser;

import com.tshark.automation.pdml.convert.Convertor;
import com.tshark.automation.pdml.convert.TypeConvertor;
import com.sun.istack.NotNull;
import lombok.Data;

/**
 * Created by onicolas on 31/08/2015.
 */
@Data
public class PdmlXmlElement<T> {
    private final String name;
    private final TypePdmlXmlElement type;
    private final Convertor<T> convertor;

    public static PdmlXmlElement get(String name) {
        return new PdmlXmlElement(name, TypePdmlXmlElement.FIELD, TypeConvertor.DEFAULT_FIELD_CONVERTOR.getConvertor());
    }

    public static <T> PdmlXmlElement get(String name,@NotNull TypePdmlXmlElement type,@NotNull Convertor<T> convertor) {
        return new PdmlXmlElement(name, type, convertor);
    }

    public String toString(){
        String xmlType= this.type == null ? "NONE" : this.type.toString();
        String convertorType = convertor == null ? "NONE" : convertor.getType().toString();
        return String.format("name: <%s> type: <%s> convertor: <%s>", name, xmlType, convertorType);
    }
}
