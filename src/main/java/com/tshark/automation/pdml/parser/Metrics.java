package com.tshark.automation.pdml.parser;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onicolas on 20/04/2015.
 */
@Data
public class Metrics {
    private List<Token>matches = new ArrayList<>();
    private List<Token> notMatches= new ArrayList<>();
}
