package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.type.Field;
import lombok.AllArgsConstructor;

/**
 * Created by onicolas on 01/09/2015.
 */
@AllArgsConstructor(staticName = "get")
public class DefaultFieldConvertor implements  Convertor<Field> {


    @Override
    public String convert(Field entity) {
        return entity.getShow();
    }

    @Override
    public TypeConvertor getType() {
        return TypeConvertor.DEFAULT_FIELD_CONVERTOR;
    }
}
