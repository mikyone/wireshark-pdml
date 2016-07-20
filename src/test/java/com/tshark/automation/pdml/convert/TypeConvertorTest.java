package com.tshark.automation.pdml.convert;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

/**
 * Created by onicolas on 01/09/2015.
 */
public class TypeConvertorTest {

    @Test
    public void testGetXmlType(){
        Assert.assertEquals(TypeConvertor.DEFAULT_FIELD_CONVERTOR, TypeConvertor.getXmlType("defaultField"));
        Assert.assertEquals(TypeConvertor.DEFAULT_PROTOCOL_CONVERTOR, TypeConvertor.getXmlType("defaultProtocol"));
        Assert.assertEquals(TypeConvertor.JSON_CONVERTOR, TypeConvertor.getXmlType("jsonConvertor"));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testGetXmlTypeFailed(){
        Assert.assertEquals(TypeConvertor.DEFAULT_FIELD_CONVERTOR, TypeConvertor.getXmlType("azeaze"));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testGetXmlTypeFailedNull(){
        Assert.assertEquals(TypeConvertor.DEFAULT_FIELD_CONVERTOR, TypeConvertor.getXmlType(null));
    }

}
