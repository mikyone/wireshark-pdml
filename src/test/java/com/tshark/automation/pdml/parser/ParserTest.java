package com.tshark.automation.pdml.parser;

import com.google.common.collect.ImmutableMap;
import com.tshark.automation.pdml.Fixture;
import com.tshark.automation.pdml.convert.TypeConvertor;
import com.tshark.automation.pdml.type.Packet;
import com.tshark.automation.pdml.type.Protocol;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by onicolas on 28/08/2015.
 */
public class ParserTest extends Fixture {

    private  Packet packet ;
    private List<PdmlXmlElement> xmlProtocols= Collections.emptyList();

    @BeforeClass
    public void setUp() throws JAXBException, IOException {
        packet = deserializePacket("packet.xml");
    }


    @Test
    public void testLookForField() {
        PdmlXmlElement pdmlXmlElement = PdmlXmlElement.get("http.request.method");

        Parser<Token> parser = Parser.get(ProcessFieldFactory.process(Arrays.asList(pdmlXmlElement), xmlProtocols));
        Optional<Token> field  = parser.lookFor(packet, pdmlXmlElement);
        Assert.assertNotNull(field.get());
        Assert.assertEquals("POST", field.get().getValue());
    }

    @Test
    public void testLookForFields(){
        PdmlXmlElement pdmlXmlElement = PdmlXmlElement.get("http.cookie_pair");
        Parser<Token> parser = Parser.get(ProcessFieldFactory.process(Arrays.asList(pdmlXmlElement), xmlProtocols));
        Map<String, List<Token>> maps = parser.lookFor(packet, Arrays.asList(pdmlXmlElement));
        Assert.assertEquals(15, maps.get(pdmlXmlElement.getName()).size());
        Assert.assertEquals("__ll_websdk_cd={}", maps.get(pdmlXmlElement.getName()).get(0).getValue());
        Assert.assertEquals("__ll_websdk_csi=31", maps.get(pdmlXmlElement.getName()).get(1).getValue());
    }

    @Test
    public void testLookForFieldsLimit()  {
        PdmlXmlElement pdmlXmlElement = PdmlXmlElement.get("http.cookie_pair");
        String key = pdmlXmlElement.getName();
        Parser<Token> parser = Parser.get(ProcessFieldFactory.process(Arrays.asList(pdmlXmlElement), xmlProtocols));
        Map<String, List<Token>> maps = parser.limit(10).lookFor(packet, Arrays.asList(pdmlXmlElement));
        Assert.assertEquals(10, maps.get(key).size());
        Assert.assertEquals("__ll_websdk_cd={}", maps.get(key).get(0).getValue());
        Assert.assertEquals("__ll_websdk_lot=1440663138", maps.get(key).get(9).getValue());
    }


    @Test
    public void testLookForFieldWithFilter() {
        String key = "http.cookie_pair";
        Map<String,String>search = ImmutableMap.of(key, "__ll_websdk_csi=31");
        Parser<String> parser = Parser.get(ProcessFieldFactory.filter(search));
        Map<String, List<String>> result = parser.lookFor(packet, Arrays.asList(PdmlXmlElement.get(key)));
        Assert.assertEquals(1, result.get(key).size());
        Assert.assertEquals(search.get(key), result.get(key).get(0));
    }

    @Test
    public void testLookForProtocol() {
        PdmlXmlElement<Protocol> pdmlXmlElement = new PdmlXmlElement("json", TypePdmlXmlElement.PROTOCOL, TypeConvertor.DEFAULT_PROTOCOL_CONVERTOR.getConvertor());
        Parser<Token> parser = Parser.get(ProcessFieldFactory.process(Arrays.asList(), Arrays.asList(pdmlXmlElement)));
        Optional<Token> token  = parser.lookFor(packet, pdmlXmlElement);
        Assert.assertNotNull(token.get());
        Assert.assertEquals("JavaScript Object Notation: application/json", token.get().getValue());
    }

    @Test
    public void testLookForFieldWithFilterHasToMatch() {
        String key = "http.connection";
        String keyPost = "http.request.method";
        boolean hasToMatch = false;
        testHasToMatch(key, keyPost, hasToMatch = false);
        testHasToMatch(key, keyPost, hasToMatch = true);
    }



    private void testHasToMatch(String key, String keyPost, boolean hasToMatch) {
        Map<String,String> search = ImmutableMap.of(key, "keep-alive", keyPost, "POSTIT");
        Parser<String> parser = Parser.get(ProcessFieldFactory.filter(search));

        List<PdmlXmlElement> pdmlFields = Arrays.asList(PdmlXmlElement.get(key), PdmlXmlElement.get(keyPost));
        Map<String, List<String>> result = parser.hasToMatch(hasToMatch).lookFor(packet, pdmlFields);
        Assert.assertEquals(1, parser.getMetrics().getNotMatches().size());
        Assert.assertNull(result.get(keyPost));
        if (!hasToMatch) {
            Assert.assertEquals(1, parser.getMetrics().getMatches().size());
            Assert.assertEquals(1, result.get(key).size());
            Assert.assertEquals(search.get(key), result.get(key).get(0));
        }else{
            Assert.assertEquals(0, parser.getMetrics().getMatches().size());
            Assert.assertNull(result.get(key));
        }

    }


    @Test
    public void testMetrics() {
        String keyCookie = "http.cookie_pair";
        String keyMethod = "http.request.method";
        List<PdmlXmlElement> pdmlFields = Arrays.asList(PdmlXmlElement.get(keyCookie), PdmlXmlElement.get(keyMethod));
        Parser<Token> parser = Parser.get(ProcessFieldFactory.process(pdmlFields, xmlProtocols));
        Map<String, List<Token>> result = parser.lookFor(packet, pdmlFields);
        Assert.assertEquals(15, result.get(keyCookie).size());
        Assert.assertEquals(1, result.get(keyMethod).size());
        Assert.assertEquals(16, parser.getMetrics().getMatches().size());
        Assert.assertEquals(0, parser.getMetrics().getNotMatches().size());
    }


}
