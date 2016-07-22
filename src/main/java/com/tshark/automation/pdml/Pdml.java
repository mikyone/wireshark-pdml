package com.tshark.automation.pdml;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.tshark.automation.pdml.parser.Parser;
import com.tshark.automation.pdml.parser.Token;
import com.tshark.automation.pdml.type.Packet;
import lombok.Data;
import lombok.SneakyThrows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by onicolas on 27/08/2015.
 */

public class Pdml {



    public static void main(String[] args) {
        Arguments arguments = Pdml.parseArgs(args);
        PdmlConfiguration pdmlArgs = new PdmlConfiguration(arguments);
        Pdml pdml = new Pdml();
        pdml.readTextByline(pdmlArgs);
    }


    public Pdml(){

    }

    @SneakyThrows
    void readTextByline(PdmlConfiguration config)  {
        try (BufferedReader br = new BufferedReader(new FileReader(config.getInputFile()))) {
            String line;
            StringBuilder xmlPacket = new StringBuilder();
            Boolean started= false;
            while ((line = br.readLine()) != null) {
                if (line.contains("<packet>")) {
                    started = true;
                }
                else if(line.contains("</packet>")){
                    xmlPacket.append(line);
                    handleXmlPacket(xmlPacket.toString(), config);
                    config.getReportMaker().writeReport();
                    started = false;
                    xmlPacket= new StringBuilder();
                }
                if (started){
                    xmlPacket.append(line);
                }
            }
        }
    }


    void handleXmlPacket(String xmlPacket, PdmlConfiguration pdmlConfiguration) throws JAXBException, IOException {
        Packet packet =  deserializePacket(xmlPacket);
        if (packet == null){
            System.err.println("Can't handle this packet"  + xmlPacket);
            return;
        }
        if(!filter(packet, pdmlConfiguration)){
            handlePacket(packet, pdmlConfiguration);
        }
    }




    private boolean filter(Packet packet, PdmlConfiguration cfg) {
        if(!cfg.getFilters().isEmpty()) {
            Parser<String>parser = Parser.get(cfg.getFilter());
            Map<String, List<String>> keyFields = parser.hasToMatch(true).limit(cfg.getLimit()).lookFor(packet, cfg.getXmlFilters());
            if(parser.getMetrics().getNotMatches().size()>0 || parser.getMetrics().getMatches().size() == 0){
                cfg.getReportMaker().filterCurrentLine(cfg.getFilters(), parser.getMetrics());
                return true;
            }
        }
        return false;
    }


    Packet deserializePacket(String xmlPacket) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Packet.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Packet) unmarshaller.unmarshal(new ByteArrayInputStream(xmlPacket.getBytes()));
    }


    private void handlePacket(Packet packet, PdmlConfiguration config) {
        Map<String, List<Token>>keyFields =  Parser.get(config.getFieldProcessor()).lookFor(packet, config.getXmlFields(), config.getXmlProtocols());
        List<String> pdmlNames = config.getXmlElements().stream().map(xmlelt-> xmlelt.getName()).collect(Collectors.toList());
        config.getReportMaker().processLine(pdmlNames, keyFields);

    }




    private static Arguments parseArgs(String[] args) {
        Arguments arguments = new Arguments();
        JCommander commander = new JCommander(arguments, args);

        if(arguments.help){
            commander.usage();
            System.exit(-1);
        }
        return arguments;
    }



    @Data
    @Parameters(separators = "=", commandDescription = "Tools to decode pdml file created from tshark")
    static class Arguments {
        @Parameter(names = {"-f","--file"}, description = "xml file with rules of parsing", required = true)
        private String fileName;

        @Parameter(names = {"-h", "--help"}, help = true)
        private boolean help;

    }
}
