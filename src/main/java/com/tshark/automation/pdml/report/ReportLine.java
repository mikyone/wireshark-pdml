package com.tshark.automation.pdml.report;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
* Created by onicolas on 31/08/2015.
*/
@Data
public class ReportLine {
    List<FieldDetail> fieldDetails = new ArrayList<>();
    List<String> reasons =  new ArrayList<>();

}
