package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.type.Field;
import lombok.AllArgsConstructor;

/**
 * Created by onicolas on 02/09/2015.
 */
@AllArgsConstructor(staticName = "get")
public class HexFieldConvertor implements Convertor<Field>{


    @Override
    public String convert(Field field) {
        return convert2Hex(field.getShowname());
    }

    private String convert2Hex(String hex) {
        String[] list=hex.split("(?<=\\G.{2})");
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(list.length);
        for(String str: list) {
            stringBuilder.append(((char) Byte.parseByte(str, 16)));
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public TypeConvertor getType() {
        return TypeConvertor.HEX_CONVERTOR;
    }


}
