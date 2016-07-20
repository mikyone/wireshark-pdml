package com.tshark.automation.pdml;

import com.tshark.automation.pdml.config.ConfigLoader;
import com.tshark.automation.pdml.config.Configuration;
import com.tshark.automation.pdml.config.Parameter;
import com.tshark.automation.pdml.convert.Convertor;
import com.tshark.automation.pdml.convert.TypeConvertor;
import com.tshark.automation.pdml.parser.PdmlXmlElement;
import com.tshark.automation.pdml.parser.ProcessFieldFactory;
import com.tshark.automation.pdml.parser.Token;
import com.tshark.automation.pdml.parser.TypePdmlXmlElement;
import com.tshark.automation.pdml.parser.processor.ProcessField;
import com.tshark.automation.pdml.report.ReportMaker;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by onicolas on 31/08/2015.
 */
@Data
public class PdmlConfiguration {

    private static final Logger logger = LogManager.getLogger(PdmlConfiguration.class.getName());

    private File inputFile;
    private String separator;


    private Map<String, String> filters = new HashMap<>();

    private List<PdmlXmlElement> xmlElements = new ArrayList<>();
    private List<PdmlXmlElement> xmlFields = new ArrayList<>();
    private List<PdmlXmlElement> xmlProtocols = new ArrayList<>();
    private List<PdmlXmlElement> xmlFilters = new ArrayList<>();
    private final ReportMaker reportMaker = ReportMaker.get();

    private ProcessField<Token> fieldProcessor;
    private ProcessField<String> filter;


    @SneakyThrows
    public PdmlConfiguration(Pdml.Arguments arguments) {
        Configuration configuration = loadXmlFile(arguments.getFileName());
        check(configuration);
    }

    @SneakyThrows
    public PdmlConfiguration(Configuration configuration) {
        check(configuration);
    }

    private void check(Configuration configuration) {
        checkSeparator(configuration);
        checkFilter(configuration);
        checkOutputs(configuration);
        inputFile = checkFile(configuration.getInputFile());

    }


    private void checkOutputs(Configuration configuration) {
        for (Parameter parameter : configuration.getOutputs()) {
            TypePdmlXmlElement typePdmlXmlElement = parameter.getType() != null ? TypePdmlXmlElement.getXmlType(parameter.getType()) : TypePdmlXmlElement.FIELD;
            PdmlXmlElement xmlElement = PdmlXmlElement.get(parameter.getName(), typePdmlXmlElement, getConvertor(parameter, typePdmlXmlElement));
            if(typePdmlXmlElement == TypePdmlXmlElement.FIELD){
                xmlFields.add(xmlElement);
            }else{
                xmlProtocols.add(xmlElement);
            }
            xmlElements.add(xmlElement);
            logger.debug("ouput :" + xmlElement.toString());
        }
    }

    @SneakyThrows
    private Configuration loadXmlFile(String fileName) throws JAXBException, IOException {
        File xmlFile = checkFile(fileName);
        return ConfigLoader.getConfiguration(xmlFile);
    }

    private void checkFilter(Configuration arguments) {
        if (arguments.getFilters() != null && !arguments.getFilters().isEmpty()) {
            for (Parameter parameter : arguments.getFilters()) {
                filters.put(parameter.getName(), parameter.getValue());
                TypePdmlXmlElement typePdmlXmlElement = parameter.getType() != null ? TypePdmlXmlElement.getXmlType(parameter.getType()) : TypePdmlXmlElement.FIELD;
                PdmlXmlElement xmlFilter = PdmlXmlElement.get(parameter.getName(), typePdmlXmlElement, getConvertor(parameter, typePdmlXmlElement));
                xmlFilters.add(xmlFilter);
                logger.debug("filter :" + xmlFilter.toString());
            }
        }
    }


    private <T> Convertor<T> getConvertor(Parameter parameter, TypePdmlXmlElement type) {
        if (parameter.getConvertor() != null) {
            return TypeConvertor.getXmlType(parameter.getConvertor()).getConvertor();
        }
        switch (type) {
            case PROTOCOL:
                return TypeConvertor.DEFAULT_PROTOCOL_CONVERTOR.getConvertor();
            case FIELD:
            default:
                return TypeConvertor.DEFAULT_FIELD_CONVERTOR.getConvertor();
        }

    }

    private void checkSeparator(Configuration config) {
        separator = config.getSeparator() == null ? ";" : config.getSeparator();
        logger.debug("separator: <" + separator+">");
        reportMaker.setSeparator(separator);
    }

    private File checkFile(String fileName) {
        logger.debug("input file to open :<" + fileName +">");
        File file = new File(fileName);
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("file <" + fileName + "> can't be opened");
        }
        return file;
    }

    public ProcessField<Token> getFieldProcessor() {
        if(fieldProcessor == null){
            fieldProcessor = ProcessFieldFactory.process(xmlFields, xmlProtocols);
        }
        return fieldProcessor;
    }


    public ProcessField<String> getFilter() {
        if(filter == null){
            filter = ProcessFieldFactory.filter(getFilters());
        }
        return filter;
    }
}
