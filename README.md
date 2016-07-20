Project to decode a pdml file created from tshark.
=================================================

This is a tool which is able to decode a pdml format file and give a result in a file or in the console.
This tool was created because it seems there is no solution to get json data on a single line with tshark


If you wish to run and install the sample code you can do this using maven as per below:

    mvn clean install

If you need to create a jar including the dependencies and potentially executable used maven as per bellow:

    mvn package

To run the jar and get the help you can execute this command

`
    java -jar wireshark-pdml -h
`


To configure the tool you need to embedded a xml file. For instance :

```
<configuration>
    <input>C:\dev\projects\wireshark-pdml\src\test\resources\pdml.xml</input>
    <separator>|</separator>
    <filters>
        <filter type="field" name="http.request.uri" value="/betslip/1.10/place"/>
        <filter type="protocol" name="json" value="JavaScript Object Notation: application/json"/>
    </filters>
    <outputs>
        <output type="field" name="timestamp"/>
        <output type="field" name="http.request.method"/>
        <output type="field" name="http.request.uri"/>
        <output type="protocol" name="json" convertor="jsonConvertor"/>
    </outputs>
</configuration>
```

This relies on access to the SET [Nexus](http://autotestweb-001:8081/nexus/index.html#welcome)

