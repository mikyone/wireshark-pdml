package com.tshark.automation.pdml.parser;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

/**
 * Created by onicolas on 01/09/2015.
 */
public class TypePdmlXmlElementTest {

    @Test
    public void testGetXmlType(){
        Assert.assertEquals(TypePdmlXmlElement.FIELD, TypePdmlXmlElement.getXmlType("field"));
        Assert.assertEquals(TypePdmlXmlElement.PROTOCOL, TypePdmlXmlElement.getXmlType("protocol"));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testGetXmlTypeFailed(){
        TypePdmlXmlElement.getXmlType("azeaze");
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testGetXmlTypeFailedNull(){
        TypePdmlXmlElement.getXmlType(null);
    }

}
