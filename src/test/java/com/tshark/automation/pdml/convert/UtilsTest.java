package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.builder.FieldBuilder;
import com.tshark.automation.pdml.type.Field;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by onicolas on 27/08/2015.
 */
public class UtilsTest {

    @Test
    public void  extractKeyTest() {
        Field member = FieldBuilder.get().name("json.member").showname("Member Key: &quot;bets&quot;").fields(new ArrayList<>()).build();
        Assert.assertEquals(" &quot;bets&quot;", Utils.extractKey(member));

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void extractKeyTestFailed() {
        Field member= FieldBuilder.get().name("json.member").showname("Member Key &quot;bets&quot;").fields(new ArrayList<>()).build();
        Assert.assertEquals("", Utils.extractKey(member));
    }


    @Test
    public void testTrimComma(){
        Assert.assertEquals("coucou", Utils.trimComma("coucou   ,"));
        Assert.assertEquals("coucou", Utils.trimComma("coucou"));
        Assert.assertEquals("coucou, coucou", Utils.trimComma("coucou, coucou,"));

    }


    @Test
    public void testTrim(){
        Assert.assertNull(Utils.trim(null));
    }

}