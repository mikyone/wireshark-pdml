package com.tshark.automation.pdml.parser;

import com.tshark.automation.pdml.parser.processor.FilterByValue;
import com.tshark.automation.pdml.parser.processor.DefaultProcessField;
import com.tshark.automation.pdml.parser.processor.ProcessField;

import java.util.List;
import java.util.Map;

/**
 * Created by onicolas on 28/08/2015.
 */
public class ProcessFieldFactory {



    /**
     * Get the first element and stop
     * @return
     */
    public static ProcessField<Token> process(List<PdmlXmlElement> xmlElements, List<PdmlXmlElement> xmlProtocols) {

        return new DefaultProcessField(xmlElements, xmlProtocols);
    }

    /**
     * Get the first element and stop
     * @return
     */
    public static ProcessField<String> filter(final Map<String, String> mapNameValue) {
        return new FilterByValue(mapNameValue);
    }


}
