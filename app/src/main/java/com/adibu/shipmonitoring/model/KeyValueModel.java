package com.adibu.shipmonitoring.model;

public class KeyValueModel {

    String key;
    String value;

    public KeyValueModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValueModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
