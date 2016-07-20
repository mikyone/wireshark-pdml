package com.tshark.automation.pdml.convert;

/**
 * Created by onicolas on 27/08/2015.
 */

public interface Convertor<T> {

    String convert(T entity);

    TypeConvertor getType();
}
