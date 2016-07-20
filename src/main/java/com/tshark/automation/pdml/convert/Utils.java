package com.tshark.automation.pdml.convert;

import com.tshark.automation.pdml.type.Field;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by onicolas on 27/08/2015.
 */
public class Utils {

    private Utils(){}

    public static String extractKey(Field field){
        Pattern p = Pattern.compile("(:)(.*)");
        Matcher matcher = p.matcher(field.getShowname());
        if (matcher.find()) {
            return matcher.group(2);
        }
        throw new IllegalArgumentException("Field showname is not compliant " +  field.getShowname());
    }

    public static String trim(String s){
        return s != null ? trimComma(s) : s;
    }

    static String trimComma(String s) {
        return s.replaceAll("\\s*(,$)","") ;
    }


}
