package com.adem.producer.writer.model;

import java.util.List;
import java.util.Set;

public class MethodModel {
    private String methodName;
    private String serviceFieldName;
    private String serviceMethodName;
    private String returnTypeName;
    private String annotationName;
    private List<ParameterModel> parameterList;
    private List<ParameterModel> serviceParameterListSorted;
    private boolean voidMethod;
    private Set<Class<?>> importSet;

    public Set<Class<?>> getImportSet() {
        return importSet;
    }

    public void setImportSet(Set<Class<?>> importSet) {
        this.importSet = importSet;
    }

    public boolean isVoidMethod() {
        return voidMethod;
    }

    public void setVoidMethod(boolean voidMethod) {
        this.voidMethod = voidMethod;
    }

    public List<ParameterModel> getServiceParameterListSorted() {
        return serviceParameterListSorted;
    }

    public void setServiceParameterListSorted(List<ParameterModel> serviceParameterListSorted) {
        this.serviceParameterListSorted = serviceParameterListSorted;
    }

    public String getServiceFieldName() {
        return serviceFieldName;
    }

    public void setServiceFieldName(String serviceFieldName) {
        this.serviceFieldName = serviceFieldName;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getServiceMethodName() {
        return serviceMethodName;
    }

    public void setServiceMethodName(String serviceMethodName) {
        this.serviceMethodName = serviceMethodName;
    }

    public String getReturnTypeName() {
        return returnTypeName;
    }

    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }

    public List<ParameterModel> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ParameterModel> parameterList) {
        this.parameterList = parameterList;
    }
}
