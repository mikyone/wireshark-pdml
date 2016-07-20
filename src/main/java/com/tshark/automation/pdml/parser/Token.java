package com.tshark.automation.pdml.parser;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by onicolas on 01/09/2015.
 */
@Data
@AllArgsConstructor(staticName = "get")
public class Token {
    private final String name;
    private final String value;
}
