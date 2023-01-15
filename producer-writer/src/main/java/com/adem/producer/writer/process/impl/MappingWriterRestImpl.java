package com.adem.producer.writer.process.impl;

import com.adem.producer.writer.exception.MethodNotAvailableException;
import com.adem.producer.writer.model.MethodModel;
import com.adem.producer.writer.model.ParameterModel;
import com.adem.producer.writer.process.MappingWriter;
import com.adem.producer.writer.util.PropertyReader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.reflections.Reflections;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.Methods;

public class MappingWriterRestImpl extends BaseWriter implements MappingWriter {

    @Override
    public String processMapping(Method method, String serviceFieldName, Configuration freemarkerConfiguration, Set<Class<?>> importSet) throws MethodNotAvailableException {
        if (method == null || freemarkerConfiguration == null) {
            throw new IllegalArgumentException("Method and configuration must not be null");
        }
        importSet.add(ResponseEntity.class);
        importSet.add(PostMapping.class);
        importSet.add(GetMapping.class);
        importSet.add(RequestParam.class);
        importSet.add(io.swagger.v3.oas.annotations.Parameter.class);//for openAPi

        List<ParameterModel> parameterList = getParameterModelList(method.getParameters());

        Class<?> returnClass = method.getReturnType();

        MethodModel methodModel = new MethodModel();
        methodModel.setParameterList(parameterList);
        methodModel.setServiceParameterListSorted(parameterList.stream().sorted(Comparator.comparingInt(ParameterModel::getOrder)).collect(Collectors.toList()));
        methodModel.setMethodName(method.getName());
        methodModel.setServiceFieldName(serviceFieldName);
        methodModel.setReturnTypeName(createReturnTypeNameGeneric(method));
        methodModel.setVoidMethod(returnClass == Void.class || returnClass == void.class);
        methodModel.setServiceMethodName(method.getName());
        methodModel.setAnnotationName(getMappingAnnotation(method));

        try {
            Template template = freemarkerConfiguration.getTemplate("mapping.ftl");
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("method", methodModel);


            StringWriter writer = new StringWriter();
            template.process(modelMap, writer);
            if (returnClass != Void.class) {
                importSet.add(returnClass);
            }
            for (ParameterModel parameter : parameterList) {
                importSet.add(parameter.getParameterClass());
            }
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MethodNotAvailableException(e.getMessage());
        }
    }

    private String createReturnTypeNameGeneric(Method method) {
        Class<?> returnClass = method.getReturnType();
        if (!returnClass.isPrimitive()) {
            if (returnClass.getTypeParameters().length > 0) {
                if(returnClass.getTypeParameters().length==1){
                    Type innerClass = ((ParameterizedType)method.getGenericReturnType()).getActualTypeArguments()[0];
                    if(innerClass instanceof ParameterizedType){
                        throw new IllegalArgumentException("Nested Generic types are not currently supported.Type:[" + method.getGenericReturnType() + "]");
                    }
                    return returnClass.getSimpleName()+"<"+((Class<?>)innerClass).getSimpleName()+">";
                }
                throw new IllegalArgumentException("Generic types are not currently supported.Type:[" + returnClass + "]");
            }
        }
        return createReturnTypeNameGeneric(method.getReturnType());
    }


    private String createReturnTypeNameGeneric(Class<?> returnClass) {
        if (returnClass == null) {
            return "UNKNOWN";
        }
        if (returnClass == Void.class || returnClass == void.class) {
            return "?";
        }
        if (returnClass.isPrimitive()) {
            if (returnClass == int.class) {
                return "Integer";
            } else if (returnClass == boolean.class) {
                return "Boolean";
            } else if (returnClass == double.class) {
                return "Double";
            } else if (returnClass == float.class) {
                return "Float";
            } else if (returnClass == long.class) {
                return "Long";
            } else if (returnClass == char.class) {
                return "Character";
            }
            return "PRIMITIVE_" + returnClass.getName();
        } else {
            if (returnClass.getTypeParameters().length > 0) {
                throw new IllegalArgumentException("Generic types are not currently supported.Type:[" + returnClass + "]");
            }
            return returnClass.getSimpleName();
        }
    }


    private List<ParameterModel> getParameterModelList(Parameter[] parameterArray) {
        List<ParameterModel> parameterModelList = new ArrayList<>();
        if (parameterArray != null && parameterArray.length > 0) {
            for (int i = 0, parameterArrayLength = parameterArray.length; i < parameterArrayLength; i++) {
                Parameter parameter = parameterArray[i];
                if (isGeneric(parameter)) {
                    throw new IllegalArgumentException("Generic types are not currently supported.Type:[" + parameter.getType() + "]");
                }
                ParameterModel parameterModel = new ParameterModel();
                parameterModel.setOrder(i);
                parameterModel.setType(createReturnTypeNameGeneric(parameter.getType()));
                parameterModel.setName(parameter.getName());
                parameterModel.setParameterClass(parameter.getType());
                boolean isLoggedParameter = parameter.getType() == PropertyReader.getLoggedParameter();
                parameterModel.setAnnotation(isLoggedParameter ? "@Parameter(hidden = true) " : getParameterAnnotation(parameter));
                parameterModelList.add(parameterModel);
            }
        }
        List<ParameterModel> finalPropertyModelList = new ArrayList<>();
        if (PropertyReader.existLoggedParameter()) {
            ParameterModel parameterModel = parameterModelList.stream().filter(x -> x.getParameterClass() == PropertyReader.getLoggedParameter()).findFirst().orElse(null);
            if (parameterModel != null) {
                finalPropertyModelList.add(parameterModel);
            }
        }
        finalPropertyModelList.addAll(parameterModelList.stream().filter(x -> x.getParameterClass() != PropertyReader.getLoggedParameter()).collect(Collectors.toList()));


//        if(PropertyReader.existLoggedParameter()) {
//            for (int i = 0; i < parameterModelList.size(); i++) {
//                ParameterModel parameter = parameterModelList.get(i);
//                Class<?> type = parameter.getParameterClass();
//                if(type==PropertyReader.getLoggedParameter() && i!=0){
//
//                    throw new IllegalArgumentException("Logged parameter supporting only first");
//                }
//            }
//        }
        return finalPropertyModelList;
    }

    private String getParameterAnnotation(Parameter parameter) {
        return "@RequestParam(\"" + parameter.getName() + "\") ";
    }

    private boolean isGeneric(Parameter parameter) {
        if (parameter == null) {
            return false;
        }
        return parameter.getParameterizedType() instanceof ParameterizedType;
    }

    private String getMappingAnnotation(Method method) {
        String methodName = method.getName();
        if (methodName.startsWith("get") || methodName.startsWith("is") || methodName.startsWith("equals")) {
            if (isAvailableGetMappingForParameters(method.getParameters())) {
                return "@GetMapping(\"" + getUrlName(methodName) + "\")";
            }
        }
        return "@PostMapping(\"" + getUrlName(methodName) + "\")";
    }


    private boolean isAvailableGetMappingForParameters(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            if (PropertyReader.existLoggedParameter() && PropertyReader.getLoggedParameter() == parameter.getType()) {
                continue;
            }
            if (!parameter.getType().getName().startsWith("java.lang")) {
                return false;
            }
        }
        return true;
    }
}
