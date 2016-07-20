package com.tshark.automation.pdml.config;

import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by onicolas on 01/09/2015.
 */
public class ConfigLoader {

    public static  Configuration getConfiguration(File xmlFile) throws JAXBException, IOException {
        String xmlConfig = IOUtils.toString(new FileInputStream(xmlFile));
        return getConfiguration(xmlConfig);
    }

    public static  Configuration getConfiguration(String xmlConfig) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Configuration) unmarshaller.unmarshal(new ByteArrayInputStream(xmlConfig.getBytes()));
    }


}
