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
        Optional<Protocol>protocol = packet.getProtocols().stream().filter(s -> s.getName().equals(JSON_PROTOCOL)).findAny();
        if(!protocol.isPresent()){
            return Optional.empty();
        }
        return Optional.of(Utils.trim(buildObject(protocol.get().getFields())));
    }

    public String convert(Protocol protocol) {
        return Utils.trim(buildObject(protocol.getFields()));
    }

    @Override
    public TypeConvertor getType() {
        return TypeConvertor.JSON_CONVERTOR;
    }

    private String buildObject(List<Field> fields) {
        String concat = "";
        for (Field field : fields) {
            switch (JsonDescriptionField.get(field.getName())) {
                case OBJECT:
                    concat += "{" + Utils.trim(buildObject(field.getFields()))+ "},";
                    break;
                case ARRAY:
                    concat += "[" + Utils.trim(buildObject(field.getFields())) + "],";
                    break;
                case MEMBER:
                    concat += getFieldMember(field) + ":" + buildObject(field.getFields()) ;
                    break;
                case VALUE:
                    return "\""+ field.getShow() + "\",";
                default:
                    throw new IllegalArgumentException("Description unknown " + JsonDescriptionField.get(field.getName()));
            }
        }
        return concat;
    }



    private String getFieldMember(Field f) {
        String name = Utils.extractKey(f).trim();
        return StringEscapeUtils.unescapeHtml4(name);
    }



}
