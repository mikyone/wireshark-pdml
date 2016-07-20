package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.type.Protocol;
import lombok.AllArgsConstructor;

/**
 * Created by onicolas on 01/09/2015.
 */
@AllArgsConstructor(staticName = "get")
public class DefaultProtocolConvertor implements Convertor<Protocol> {

    @Override
    public String convert(Protocol entity) {
         return entity.getShowname();
    }

    @Override
    public TypeConvertor getType() {
        return TypeConvertor.DEFAULT_PROTOCOL_CONVERTOR;
    }


}
