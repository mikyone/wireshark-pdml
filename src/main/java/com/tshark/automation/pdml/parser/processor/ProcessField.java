package com.tshark.automation.pdml.parser.processor;

import com.tshark.automation.pdml.type.Field;
import com.tshark.automation.pdml.type.Protocol;

/**
 * Created by onicolas on 28/08/2015.
 */
public interface  ProcessField<T> {

    T processField(Field field);

    T processProtocol(Protocol protocol);

}
