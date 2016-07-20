package com.tshark.automation.pdml;

import com.tshark.automation.pdml.config.ConfigLoader;
import com.tshark.automation.pdml.config.Configuration;
import com.tshark.automation.pdml.type.Packet;
import lombok.Data;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onicolas on 28/08/2015.
 */
public class PdmlTest extends Fixture {
    private static final String EXPECTED_FIRST_PACKET = "    <packet>        <b>Testdjifjsdolrngdlgn</b>    </packet>";


    @Test
    public void testDeserializePacket() throws JAXBException, IOException {
        Packet packet = deserializePacket("packet.xml");
        Assert.assertNotNull(packet);
    }


    @Test(enabled = false)
    //false
    public static void test() throws IOException, URISyntaxException, JAXBException {
        String path= Pdml.class.getClassLoader().getResource("PdmlConfiguration.xml").getPath();
        Configuration configuration = ConfigLoader.getConfiguration(new File(path));
        configuration.setInputFile(Pdml.class.getClassLoader().getResource("pdml.xml").getPath());

        Pdml.Arguments arguments = new Pdml.Arguments();
        arguments.setFileName(path);

        Pdml pdml = new Pdml();
        pdml.readTextByline(new PdmlConfiguration(configuration));
    }


    @Test
    public static void testFileInput() throws IOException, URISyntaxException, JAXBException {
        URL url = Pdml.class.getClassLoader().getResource("FakeConfiguration.xml");
        Configuration configuration = ConfigLoader.getConfiguration(new File(url.getPath()));
        configuration.setInputFile(Pdml.class.getClassLoader().getResource("boubou.xml").getPath());

        PdmlMock pdmlMock = new PdmlMock();
        pdmlMock.readTextByline(new PdmlConfiguration(configuration));
        Assert.assertEquals(5, pdmlMock.getPackets().size());
        Assert.assertEquals(EXPECTED_FIRST_PACKET, pdmlMock.getPackets().get(0));
    }

    @Data static class PdmlMock extends Pdml {
        private final List<String> packets = new ArrayList<>();

        PdmlMock() {
            super();
        }

        @Override
        public Packet deserializePacket(String xmlPacket) throws JAXBException {
            packets.add(xmlPacket);
            return null;
        }
    }
}


