package com.tshark.automation.pdml.report;

import com.tshark.automation.pdml.parser.Metrics;
import com.tshark.automation.pdml.parser.Token;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class builds report
 * Created by onicolas on 31/08/2015.
 */
@Data
@NoArgsConstructor(staticName = "get")
public class ReportMaker {

    private static final Logger logger = LogManager.getLogger(ReportMaker.class.getName());

    private static final String EXPECTED_FILTER = "Expected to apply this filter <%s=%s> but the value was <%s>";
    private static final String NO_TOKEN_FOUND = "Applied this filter %s but no value has been found";
    private String separator = ";";
    private ReportLine  currentLine;
    private int packetNumber = 0;



    public ReportMaker setFieldSeparators(String separator){
        setSeparator(separator);
        return this;
    }

    public void processLine(List<String> argFields, Map<String, List<Token>> keyFields) {
        currentLine = new ReportLine();
        for (String nameField: argFields) {
            List<Token> fields = keyFields.get(nameField);
            List<String> showValues = fields == null ? Arrays.asList("field missing : <"+ nameField + ">") : fields.stream().map(s -> s.getValue()).collect(Collectors.toList());
            currentLine.getFieldDetails().add(FieldDetail.of(nameField, StringUtils.join(showValues, "|")));
        }
    }



    public void filterCurrentLine(Map<String, String> filters, Metrics metrics) {
        currentLine = new ReportLine();
        for (Token notMatch : metrics.getNotMatches()){
            String value = filters.get(notMatch.getName());
            currentLine.getReasons().add(String.format(EXPECTED_FILTER, notMatch.getName(), value, notMatch.getValue()));
        }
        if (metrics.getMatches().size() == 0){
            currentLine.getReasons().add(String.format(NO_TOKEN_FOUND , filtersPrettyPrint(filters)));
        }
    }

    private String filtersPrettyPrint(Map<String, String> filters) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<String, String> nameValue : filters.entrySet()){
            stringBuilder.append(String.format(" <%s=%s> ", nameValue.getKey(), nameValue.getValue()));
        }
        return stringBuilder.toString();
    }



    public void writeReport() {
        packetNumber++;
        if(currentLine == null){
            logger.debug(Integer.valueOf(packetNumber) + ") Rejected packet because no packet found");
            return;
        }
        if(!currentLine.getReasons().isEmpty()){
            logger.debug(Integer.valueOf(packetNumber) + ") Rejected packet");
            currentLine.getReasons().stream().forEach(reason -> logger.debug("  >  Reason : " + reason));
            return;
        }
        String line = currentLine.getFieldDetails().stream().map(field -> field.getValue()).collect(Collectors.joining(separator));
        logger.debug(Integer.valueOf(packetNumber) + ") Handled packet number ");
        logger.info(line);
    }


}
