package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.builder.FieldBuilder;
import com.tshark.automation.pdml.type.Field;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by onicolas on 01/09/2015.
 */
public class DefaultFieldConvertorTest {

    @Test
    public void testConvert(){
        String showname = "aze";
        Field field= FieldBuilder.get().show(showname).build();
        Convertor<Field> convertor = TypeConvertor.DEFAULT_FIELD_CONVERTOR.getConvertor();
        Assert.assertEquals(showname, convertor.convert(field));
    }


}
