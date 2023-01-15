package com.adem.producer.writer.model;

import java.io.Serializable;

public class ParameterModel implements Serializable {
    private static final long serialVersionUID = 4905571021771132145L;
    private Class<?> parameterClass;
    private String type;
    private String name;
    private String annotation;
    private int order;

    public Class<?> getParameterClass() {
        return parameterClass;
    }

    public void setParameterClass(Class<?> parameterClass) {
        this.parameterClass = parameterClass;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
}
