package com.tshark.automation.pdml.type;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by onicolas on 27/08/2015.
 */
@Data
public class Protocol {
    public Protocol(){}

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "pos")
    private int pos;

    @XmlAttribute(name = "showname")
    private String showname;

    @XmlAttribute(name = "size")
    private int size;

    @XmlElement(name = "field")
    private List<Field> fields ;
}
