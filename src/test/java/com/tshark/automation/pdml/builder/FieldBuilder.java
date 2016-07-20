package com.tshark.automation.pdml.builder;

import com.tshark.automation.pdml.type.Field;

import java.util.ArrayList;

/**
 * Created by onicolas on 28/08/2015.
 */
public class FieldBuilder {
    private Field field = new Field();

    private FieldBuilder(){}

    public FieldBuilder name(String name) {
        field.setName(name);
        return this;
    }

    public static FieldBuilder get() {
        return new FieldBuilder();
    }

    public FieldBuilder showname(String showname) {
        field.setShowname(showname);
        return this;
    }

    public FieldBuilder fields(ArrayList<Field> fields) {
        field.setFields(fields);
        return this;
    }

    public Field build() {
        return field;
    }

    public FieldBuilder show(String show) {
        field.setShow(show);
        return this;
    }
}