package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.builder.FieldBuilder;
import com.tshark.automation.pdml.type.Field;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by onicolas on 02/09/2015.
 */
public class HexFieldConvertorTest {

    @Test
    public void convertHex(){
        Field field = getField("0000003616005061646479506f7765722f53706f727473626f6f6b2f53706f7274732f34392f436f6d7065746974696f6e732f2e2a01");
        String value = TypeConvertor.HEX_CONVERTOR.getConvertor().convert(field);
        Assert.assertEquals("6\u0016\u0000PaddyPower/Sportsbook/Sports/49/Competitions/.*", value );
    }




    private Field getField(String hex){
        return FieldBuilder.get().name("anyway").showname(hex).fields(new ArrayList<>()).build();
    }
}
