package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.type.Field;
import com.tshark.automation.pdml.type.Packet;
import com.tshark.automation.pdml.type.Protocol;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;
import java.util.Optional;

/**
 * Created by onicolas on 27/08/2015.
 */
@AllArgsConstructor(staticName = "get")
public class JsonConvertor implements  Convertor<Protocol>{

    public static final String JSON_PROTOCOL = "json";

     public Optional<String> convert(Packet packet) {
        Optional <Protocol>protocol = packet.getProtocols().stream().filter(s -> JSON_PROTOCOL.equals(s.getName())).findAny();
        if(!protocol.isPresent()){
            return Optional.empty();
        }
        return Optional.of(buildObject(protocol.get().getFields()));
    }

    public String convert(Protocol protocol) {
        return buildObject(protocol.getFields());
    }

    @Override
    public TypeConvertor getType() {
        return TypeConvertor.JSON_CONVERTOR;
    }

    private String buildObject(List<Field> fields) {
        String concat = "";
        for (Field field : fields) {
            JsonDescriptionField descriptionField = JsonDescriptionField.get(field.getName());
            String value = "";
            switch (descriptionField) {
                case OBJECT:
                    value = Utils.trim(buildObject(field.getFields()));
                    concat += "{" + value + "},";
                    break;
                case ARRAY:
                    value =  Utils.trim(buildObject(field.getFields()));
                    concat += "[" + value + "],";
                    break;
                case MEMBER:
                    value = Utils.trim(buildObject(field.getFields()));
                    concat += getFieldMember(field) + ":" + value ;
                    break;
                case VALUE:
                    return "'"+ field.getShow() + "',,";
                default:
                    throw new IllegalArgumentException("Description unknown " + descriptionField);
            }
            ;
        }
        return Utils.trimComma(concat);
    }



    @Data
    static class JsonRaw{
        private final boolean isRawValue;

        public static JsonRaw get(boolean isRaw) {
            return new JsonRaw(isRaw);
        }
    }

    String getFieldMember(Field f) {
        String name = Utils.extractKey(f).trim();
        return StringEscapeUtils.unescapeHtml4(name);
    }



}
