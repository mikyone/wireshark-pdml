package com.tshark.automation.pdml.parser;

import com.tshark.automation.pdml.parser.processor.ProcessField;
import com.tshark.automation.pdml.type.Field;
import com.tshark.automation.pdml.type.Packet;
import com.tshark.automation.pdml.type.Protocol;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by onicolas on 28/08/2015.
 */
@Data
public class Parser<T> {

    private final ProcessField<T> fieldProcessor ;
    private int limit = 0;
    private boolean hasToMatch = false;
    private Metrics metrics = new Metrics();

    private Parser(ProcessField<T> fieldProcessor ){
        this.fieldProcessor = fieldProcessor;
    }

    public static <T> Parser<T> get(ProcessField<T> fieldProcessor){
        return new Parser(fieldProcessor);
    }

    public  Map<String, List<T>> lookFor(Packet packet, List<PdmlXmlElement> xmlFields, List<PdmlXmlElement> xmlProtocols) {
        Map<String, List<T>> map = new HashMap<>();
        for (Protocol protocol : packet.getProtocols()) {
            processProtocol(map, protocol, xmlProtocols);
            recursiveFindField(map, protocol.getFields(), xmlFields);
        }
        return map;
    }

    public Map<String, List<T>> lookFor(Packet packet, List<PdmlXmlElement> pdmlFields){
        List<PdmlXmlElement> xmlFields = pdmlFields.stream().filter(f -> f.getType() == TypePdmlXmlElement.FIELD).collect(Collectors.toList());
        List<PdmlXmlElement> xmlProtocols = pdmlFields.stream().filter(f -> f.getType() == TypePdmlXmlElement.PROTOCOL).collect(Collectors.toList());
        return lookFor(packet, xmlFields, xmlProtocols);
    }


    public Optional<T> lookFor(Packet packet, PdmlXmlElement pdmlFields){
        List<PdmlXmlElement> pdmlXmlElements= Arrays.asList(pdmlFields);
        Map<String, List<T>> map =  lookFor(packet, pdmlXmlElements);
        return  !map.isEmpty() ? Optional.of(map.get(pdmlFields.getName()).iterator().next()) : Optional.empty();
    }


    public Parser<T> limit(Optional<Integer> limit){
        this.limit= limit.orElse(0);
        return this;
    }

    public Parser<T> hasToMatch(boolean hasToMatch){
        this.hasToMatch = hasToMatch;
        return this;
    }

    private void recursiveFindField(Map<String, List<T>> map, List<Field> fields, List<PdmlXmlElement> pdmlXmlElements) {
        if (fields == null || fields.isEmpty() || stop()) {
            return ;
        }
        for (Field field : fields) {
            if (stop()) {
                return;
            }
            processField(map, field, pdmlXmlElements);

            recursiveFindField(map, field.getFields(), pdmlXmlElements);
        }
    }



    private boolean stop() {
        return reachTheLimit() || stopOnUnMatch();
    }


    private boolean reachTheLimit() {
        return limit != 0 && metrics.getMatches().size() >= limit;
    }

    private boolean stopOnUnMatch() {
        return hasToMatch ? metrics.getNotMatches().size()  > 0 : false;
    }

    private void processField(Map<String, List<T>> map, Field field, List<PdmlXmlElement> pdmlXmlElements) {
        Optional<String> name= pdmlXmlElements.stream().map(pdml -> pdml.getName()).filter(n -> n.equals(field.getName())).findFirst();
        if(name.isPresent()){
            T value = fieldProcessor.processField(field);
            Token token = Token.get(field.getName(), field.getShow());
            handlePostProcessing(map, token , name.get(), value);
        }
    }

    private void processProtocol(Map<String, List<T>> map, Protocol protocol, List<PdmlXmlElement> pdmlXmlElements) {
        Optional<String> name = pdmlXmlElements.stream().map(pdml -> pdml.getName()).filter(n -> n.equals(protocol.getName())).findFirst();
        if (name.isPresent()) {
            T value = fieldProcessor.processProtocol(protocol);
            Token token = Token.get(protocol.getName(), protocol.getShowname());
            handlePostProcessing(map, token , name.get(), value);
        }
    }

    private void handlePostProcessing(Map<String, List<T>> map, Token token, String name, T value) {
        if (value != null){
            List<T>values = map.get(name);
            if(values == null){
                values = new ArrayList<>();
                map.put(name, values);
            }
            values.add(value);
            metrics.getMatches().add(token);
        }else{
            metrics.getNotMatches().add(token);
        }
    }




}

