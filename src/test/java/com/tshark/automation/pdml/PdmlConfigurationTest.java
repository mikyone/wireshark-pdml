package com.tshark.automation.pdml;

import com.tshark.automation.pdml.config.Configuration;
import com.tshark.automation.pdml.config.Parameter;
import com.tshark.automation.pdml.convert.TypeConvertor;
import com.tshark.automation.pdml.parser.TypePdmlXmlElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by onicolas on 31/08/2015.
 */
public class PdmlConfigurationTest {

    private final String configPath = PdmlConfigurationTest.class.getClassLoader().getResource("FakeConfiguration.xml").getPath();
    private final String packetPath = PdmlConfigurationTest.class.getClassLoader().getResource("packet.xml").getPath();
    private final Pdml.Arguments arguments = new Pdml.Arguments();




    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFileNotPresent() {
        arguments.setFileName("bidon");
        new PdmlConfiguration(arguments);
    }

    @Test
    public void testFilter() throws JAXBException, IOException {
        arguments.setFileName(configPath);
        PdmlConfiguration pdmlConfiguration = new PdmlConfiguration(getConfiguration());
        Assert.assertEquals(1, pdmlConfiguration.getFilters().size());
        Assert.assertEquals("POST", pdmlConfiguration.getFilters().get("http.request.uri"));
    }



    @Test
    public void testOutputs() throws JAXBException, IOException {

        PdmlConfiguration pdmlConfiguration = new PdmlConfiguration(getConfiguration());
        Assert.assertEquals(2, pdmlConfiguration.getXmlFields().size());
        Assert.assertEquals("http.request.uri", pdmlConfiguration.getXmlFields().get(1).getName());
        Assert.assertEquals(TypePdmlXmlElement.FIELD, pdmlConfiguration.getXmlFields().get(1).getType());
        Assert.assertEquals(TypeConvertor.DEFAULT_FIELD_CONVERTOR, pdmlConfiguration.getXmlFields().get(1).getConvertor().getType());

        Assert.assertEquals(1, pdmlConfiguration.getXmlProtocols().size());
        Assert.assertEquals(TypeConvertor.JSON_CONVERTOR, pdmlConfiguration.getXmlProtocols().get(0).getConvertor().getType());

    }


    private Configuration getConfiguration() throws JAXBException {
        Configuration configuration = new Configuration();
        configuration.setInputFile(packetPath);
        configuration.setSeparator(",");
        configuration.setFilters(Arrays.asList(getParameter("http.request.uri", "POST")));
        configuration.setOutputs(Arrays.asList(getParameter("http.request.uri", "POST", "defaultField", "field"),
                                               getParameter("http.request.uri", "POST", "jsonConvertor", "protocol"),
                                               getParameter("http.request.uri", "GET", null, null)));

        return configuration;
    }

    private Parameter getParameter(String name, String value) {
        return getParameter(name, value, null, null);

    }

    private Parameter getParameter(String name, String value, String convertor, String type) {
        Parameter p = new Parameter();
        p.setName(name);
        p.setValue(value);
        p.setType(type);
        p.setConvertor(convertor);
        return p;
    }



}
