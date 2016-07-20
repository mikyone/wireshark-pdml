package com.tshark.automation.pdml.config;

import lombok.Data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by onicolas on 01/09/2015.
 */
@Data
@XmlRootElement(name = "configuration")
public class Configuration {

    @XmlElement(required= true, name = "input")
    private String inputFile ;

    @XmlElement(name="separator")
    private String separator;

    @XmlElement(required = false,name="filter")
    @XmlElementWrapper(name= "filters")
    private List<Parameter> filters;

    @XmlElement(required = true,name="output")
    @XmlElementWrapper(name= "outputs")
    private List<Parameter> outputs;

}
