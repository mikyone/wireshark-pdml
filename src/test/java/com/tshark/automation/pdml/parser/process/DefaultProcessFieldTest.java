package com.tshark.automation.pdml.parser.process;

import com.tshark.automation.pdml.builder.FieldBuilder;
import com.tshark.automation.pdml.builder.ProtocolBuilder;
import com.tshark.automation.pdml.convert.Convertor;
import com.tshark.automation.pdml.parser.PdmlXmlElement;
import com.tshark.automation.pdml.parser.Token;
import com.tshark.automation.pdml.parser.TypePdmlXmlElement;
import com.tshark.automation.pdml.parser.processor.DefaultProcessField;
import com.tshark.automation.pdml.type.Field;
import com.tshark.automation.pdml.type.Protocol;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by onicolas on 02/09/2015.
 */
public class DefaultProcessFieldTest {


    public static final String FIELD_NAME = "hexvalue";
    private static final String PROTOCOL_NAME ="protoname" ;

    @Test
    public void testProcessField() {
        String fieldValueConverted = "coucou";
        DefaultProcessField processField = prepareProcess(fieldValueConverted, "");
        Token token = processField.processField(getField(FIELD_NAME));
        Assert.assertEquals(fieldValueConverted, token.getValue());

    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "unknown_value is not there! It's a bug and it can't happen.*")
    public void testProcessFieldFailed() {
        DefaultProcessField processField = prepareProcess("", "");
        processField.processField(getField("unknown_value"));
    }

    @Test
    public void testProcessProtocol() {
        String protocolValueConverted = "coucou";
        DefaultProcessField processField = prepareProcess("", protocolValueConverted);
        Token token = processField.processProtocol(getProtocol(PROTOCOL_NAME));
        Assert.assertEquals(protocolValueConverted, token.getValue());
    }



    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "unknown_value is not there! It's a bug and it can't happen.*")
    public void testProcessProtocolFailed() {
        DefaultProcessField processField = prepareProcess("", "");
        processField.processField(getField("unknown_value"));
    }

    private DefaultProcessField prepareProcess(String fieldValueConverted, String protocolValueConverted) {
        Convertor<Field> field = PowerMockito.mock(Convertor.class);
        Convertor<Protocol> protocol= PowerMockito.mock(Convertor.class);
        PowerMockito.when(field.convert(Mockito.any())).thenReturn(fieldValueConverted);
        PowerMockito.when(protocol.convert(Mockito.any())).thenReturn(protocolValueConverted);
        return new DefaultProcessField(getXmlFields(field), getXmlProtocols(protocol));
    }

    public List<PdmlXmlElement> getXmlFields(Convertor<Field> convertor) {
        PdmlXmlElement hexvalue = PdmlXmlElement.get(FIELD_NAME, TypePdmlXmlElement.FIELD, convertor);
        PdmlXmlElement bouValue= PdmlXmlElement.get("bouValue", TypePdmlXmlElement.FIELD, convertor);
        return Arrays.asList(hexvalue, bouValue);
    }

    public List<PdmlXmlElement> getXmlProtocols(Convertor<Protocol> convertor) {
        PdmlXmlElement protoJson = PdmlXmlElement.get(PROTOCOL_NAME, TypePdmlXmlElement.PROTOCOL, convertor);
        return Arrays.asList(protoJson);
    }

    public Field getField(String fieldName) {
        return FieldBuilder.get().name(fieldName).show("coucou").build();
    }
    private Protocol getProtocol(String name) {
        return ProtocolBuilder.get().name(name).build();
    }
}
