package org.marco.authdemo.system.gdpr.attributes;

public class StringAttributeConverter extends AbstractAttributeConverter<String> {

    @Override
    protected String stringToEntityAttribute(String data) {
        return data;
    }

    @Override
    protected String entityAttributeToString(String attribute) {
        return attribute;
    }
}
