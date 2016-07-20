package com.tshark.automation.pdml.builder;

import com.tshark.automation.pdml.type.Field;
import com.tshark.automation.pdml.type.Protocol;

import java.util.ArrayList;

/**
 * Created by onicolas on 28/08/2015.
 */
public class ProtocolBuilder {
    private Protocol protocol= new Protocol();

    private ProtocolBuilder(){}

    public ProtocolBuilder name(String name) {
        protocol.setName(name);
        return this;
    }

    public ProtocolBuilder showname(String showname) {
        protocol.setShowname(showname);
        return this;
    }

    public static ProtocolBuilder get() {
        return new ProtocolBuilder();
    }

    public ProtocolBuilder fields(ArrayList<Field> fields) {
        protocol.setFields(fields);
        return this;
    }

    public Protocol build() {
        return protocol;
    }
}
