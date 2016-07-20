package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.builder.ProtocolBuilder;
import com.tshark.automation.pdml.type.Protocol;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by onicolas on 01/09/2015.
 */
public class DefaultProtocolConvertorTest {

    @Test
    public void testConvert(){
        String showname = "aze";
        Protocol protocol = ProtocolBuilder.get().showname(showname).build();
        Convertor<Protocol> convertor = TypeConvertor.DEFAULT_PROTOCOL_CONVERTOR.getConvertor();
        Assert.assertEquals(showname, convertor.convert(protocol));
    }


}
