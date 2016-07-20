package com.tshark.automation.pdml.config;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by onicolas on 01/09/2015.
 */
@Data
public class Parameter {

    @XmlAttribute(required = true, name="type")
    private String type;

    @XmlAttribute(required = true, name="name")
    private String name;

    @XmlAttribute(name="value")
    private String value;

    @XmlAttribute(name="convertor")
    private String convertor;
}
