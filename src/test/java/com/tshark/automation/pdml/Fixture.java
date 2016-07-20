package com.tshark.automation.pdml;

import com.tshark.automation.pdml.type.Packet;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by onicolas on 28/08/2015.
 */
public class Fixture  {

    protected Packet deserializePacket(String pdmlFile) throws JAXBException, IOException {
        String response = IOUtils.toString(Fixture.class.getClassLoader().getResourceAsStream(pdmlFile));
        Pdml pdml = new Pdml();
        return pdml.deserializePacket(response);
    }


}
