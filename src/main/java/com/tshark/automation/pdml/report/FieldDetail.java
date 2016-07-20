package com.tshark.automation.pdml.report;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
* Created by onicolas on 31/08/2015.
*/
@Data
@AllArgsConstructor(staticName = "of")
public class FieldDetail {
    private final String name;
    private final String value;
}
