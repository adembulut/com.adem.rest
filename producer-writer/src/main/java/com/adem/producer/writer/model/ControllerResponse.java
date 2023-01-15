package com.adem.producer.writer.model;

import java.lang.reflect.Method;
import java.util.Map;

public class ControllerResponse {
    private Map<Method,String> ignoreMappingMap;
    private String output;


    public Map<Method, String> getIgnoreMappingMap() {
        return ignoreMappingMap;
    }

    public void setIgnoreMappingMap(Map<Method, String> ignoreMappingMap) {
        this.ignoreMappingMap = ignoreMappingMap;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
