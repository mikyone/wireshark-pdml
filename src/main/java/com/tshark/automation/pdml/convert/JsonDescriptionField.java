package com.tshark.automation.pdml.convert;

/**
 * Created by onicolas on 27/08/2015.
 */
public enum JsonDescriptionField {
    OBJECT("json.object"),
    ARRAY("json.array"),
    MEMBER("json.member"),
    VALUE("json.value");

    private String description;

    JsonDescriptionField(String description) {
        this.description = description;
    }

    public static JsonDescriptionField get(String longDescription) {
        for (JsonDescriptionField jsonDescriptionField : values()) {
            if (longDescription.contains(jsonDescriptionField.description)) {
                return jsonDescriptionField;
            }
        }
        throw new IllegalArgumentException("Description unknown " + longDescription);
    }

}
