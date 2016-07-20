package com.tshark.automation.pdml.config;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by onicolas on 01/09/2015.
 */
public class ConfigLoaderTest{

    @Test
    public void testGetConfiguration() throws JAXBException, IOException {
        URLEncoder.encode("%20", "UTF-8");
        String xmlConfig = IOUtils.toString(ConfigLoaderTest.class.getClassLoader().getResourceAsStream("FakeConfiguration.xml"));
        Configuration configuration = ConfigLoader.getConfiguration(xmlConfig);
        Assert.assertNotNull(configuration);
        Assert.assertEquals(3, configuration.getOutputs().size());
        Assert.assertEquals("protocol", configuration.getOutputs().get(2).getType());
        Assert.assertEquals("json", configuration.getOutputs().get(2).getName());
        Assert.assertEquals("defaultProtocol", configuration.getOutputs().get(2).getConvertor());
        Assert.assertEquals(1, configuration.getFilters().size());
        Assert.assertEquals("POST", configuration.getFilters().get(0).getValue());

    }

}
