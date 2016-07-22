package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.builder.FieldBuilder;
import com.tshark.automation.pdml.builder.ProtocolBuilder;
import com.tshark.automation.pdml.type.Field;
import com.tshark.automation.pdml.type.Protocol;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by onicolas on 27/08/2015.
 */
public class JsonConvertorTest {


    @Test
    public void serializeJsonMember(){
        Protocol protocol = getProtocol(getMemberValue());
        String json = JsonConvertor.get().convert(protocol);
        Assert.assertEquals("\"bets\":\"SGL\"", json);
    }

    @Test
    public void serializeJsonObject(){
        Protocol protocol = getProtocol(getObject(getMemberValue()));
        String json = JsonConvertor.get().convert(protocol);
        Assert.assertEquals("{\"bets\":\"SGL\"}", json);
    }

    @Test
    public void serializeJsonArray(){
        Protocol protocol = getProtocol(getArray(getObject(getMemberValue()), getObject(getMemberValue())));
        String json = JsonConvertor.get().convert(protocol);
        Assert.assertEquals("[{\"bets\":\"SGL\"},{\"bets\":\"SGL\"}]", json);
    }

    @Test
    public void serializeComplex(){
        Protocol protocol = getProtocol(getObjectMember(getArray(getObject(getMemberValue()), getObject(getMemberValue()))));
        String json = JsonConvertor.get().convert(protocol);
        Assert.assertEquals("{\"boubou\":[{\"bets\":\"SGL\"},{\"bets\":\"SGL\"}]}", json);
    }

    private Protocol getProtocol(Field... fields){
        Protocol protocol = ProtocolBuilder.get().name(JsonConvertor.JSON_PROTOCOL).fields(new ArrayList<>()).build();
        for (Field field : fields) {
            protocol.getFields().add(field);
        }
        return protocol;
    }

    private Field getObject(Field... fields){
        Field object = FieldBuilder.get().name("json.object").showname("Object").fields(new ArrayList<>()).build();
        for (Field field : fields) {
            object.getFields().add(field);
        }
        return object;
    }
    private Field getObjectMember(Field... fields){
        Field member=  FieldBuilder.get().name("json.member").showname("Member Key: &quot;boubou&quot;").fields(new ArrayList<>()).build();
        Field object = FieldBuilder.get().name("json.object").showname("Object").fields(new ArrayList<>()).build();
        object.getFields().add(member);
        for (Field field : fields) {
            member.getFields().add(field);
        }
        return object;
    }
    private Field getArray(Field... fields){
        Field object = FieldBuilder.get().name("json.array").showname("Array").fields(new ArrayList<>()).build();
        for (Field field : fields) {
            object.getFields().add(field);
        }
        return object;
    }

    private Field getMemberValue() {
        Field member = FieldBuilder.get().name("json.member").showname("Member Key: &quot;bets&quot;").fields(new ArrayList<>()).build();
        Field value = FieldBuilder.get().name("json.value.string").showname("String value: SGL").show("SGL").fields(new ArrayList<>()).build();
        member.getFields().add(value);
        return member;
    }




}
