package com.tshark.automation.pdml.type;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by onicolas on 27/08/2015.
 */
@Data
@XmlRootElement(name = "packet")
public class Packet{

    @XmlElement(name= "proto")
    private List<Protocol> protocols;
}

