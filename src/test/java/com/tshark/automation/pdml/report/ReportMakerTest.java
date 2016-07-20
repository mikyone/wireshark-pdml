package com.tshark.automation.pdml.report;

import com.google.common.collect.ImmutableMap;
import com.tshark.automation.pdml.parser.Metrics;
import com.tshark.automation.pdml.parser.Token;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by onicolas on 31/08/2015.
 */
public class ReportMakerTest {

    @Test
    public void testProcessLine(){
        ReportMaker reportMaker = ReportMaker.get();
        String f2 = "f2";
        String f1 = "f1";
        Token field1 = Token.get(f1, "value1");
        Token field2 = Token.get(f2, "value2");
        List<String> fields= Arrays.asList(f1, f2);

        Map<String, List<Token>> maps= ImmutableMap.of(f1, Arrays.asList(field1, field2),
                                                       f2, Arrays.asList(field2));
        reportMaker.processLine(fields , maps);
        ReportLine line = reportMaker.getCurrentLine();
        Assert.assertEquals(2, line.getFieldDetails().size());
        Assert.assertEquals(f1, line.getFieldDetails().get(0).getName());
        Assert.assertEquals("value1|value2", line.getFieldDetails().get(0).getValue());
        Assert.assertEquals(f2, line.getFieldDetails().get(1).getName());
        Assert.assertEquals("value2", line.getFieldDetails().get(1).getValue());
    }


    @Test
    public void testFilterCurrentLine(){
        ReportMaker reportMaker = ReportMaker.get();
        String name = "f2";
        String filterExpected = "boubou";
        Metrics metrics = new Metrics();
        metrics.setNotMatches(Arrays.asList(Token.get(name, "coucou")));
        Map<String, String> maps= ImmutableMap.of(name, filterExpected);

        reportMaker.filterCurrentLine(maps, metrics);
        Assert.assertEquals(2, reportMaker.getCurrentLine().getReasons().size());
        Assert.assertEquals("Expected to apply this filter <f2=boubou> but the value was <coucou>" , reportMaker.getCurrentLine().getReasons().get(0));
        Assert.assertEquals("Applied this filter  <f2=boubou>  but no value has been found" , reportMaker.getCurrentLine().getReasons().get(1));
    }



}
