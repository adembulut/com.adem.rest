package com.adem.producer.writer.process.impl;

import com.adem.producer.writer.exception.MethodNotAvailableException;
import com.adem.producer.writer.model.ControllerResponse;
import com.adem.producer.writer.model.MethodModel;
import com.adem.producer.writer.process.ControllerWriter;
import com.adem.producer.writer.process.MappingWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.*;

public class ControllerWriterImpl extends BaseWriter implements ControllerWriter {
    private final MappingWriter mappingWriter;

    public ControllerWriterImpl(MappingWriter mappingWriter) {
        this.mappingWriter = mappingWriter;
    }

    @Override
    public ControllerResponse processController(Class<?> serviceClass, String packageName, Configuration freemarkerConfiguration) throws Exception {
        if (serviceClass == null || freemarkerConfiguration == null) {
            throw new IllegalArgumentException("Service and configuration must not be null");
        }
        String fieldName = getFieldName(serviceClass.getSimpleName());
        String baseUrl = getUrlName(serviceClass.getSimpleName());
        List<String> mappingCodes = new ArrayList<>();
        Set<Class<?>> rawImportSet = new HashSet<>();
        rawImportSet.add(serviceClass);

        ControllerResponse response = new ControllerResponse();
        response.setIgnoreMappingMap(new HashMap<>());

        Method[] methods = serviceClass.getMethods();
        for (Method method : methods) {
            try {
                MethodModel methodModel = mappingWriter.processMapping(method, fieldName, freemarkerConfiguration);

                mappingCodes.add(processMethodCode(freemarkerConfiguration, methodModel));
                rawImportSet.addAll(methodModel.getImportSet());
            } catch (Exception e) {
                response.getIgnoreMappingMap().put(method, e.getMessage());
            }
        }

        Template template = freemarkerConfiguration.getTemplate("controller.ftl");
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("importSet", getImportList(rawImportSet));
        modelMap.put("className", serviceClass.getSimpleName());
        modelMap.put("baseUrl", baseUrl);
        modelMap.put("serviceFieldName", fieldName);
        modelMap.put("serviceName", serviceClass.getSimpleName());
        modelMap.put("mappingCodes", mappingCodes);
        modelMap.put("packageName", packageName);


        StringWriter writer = new StringWriter();
        template.process(modelMap, writer);
        response.setOutput(writer.toString());
        return response;
    }

    private String processMethodCode(Configuration configuration, MethodModel methodModel) throws MethodNotAvailableException {
        try {
            Template template = configuration.getTemplate("mapping.ftl");
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("method", methodModel);


            StringWriter writer = new StringWriter();
            template.process(modelMap, writer);

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MethodNotAvailableException(e.getMessage());
        }
    }

    private List<Class<?>> getImportList(Set<Class<?>> raw) {
        if (raw == null || raw.isEmpty()) {
            return new ArrayList<>();
        }
        List<Class<?>> finaList = new ArrayList<>();
        for (Class<?> aClass : raw) {
            if (aClass == null || aClass.isPrimitive()) {
                continue;
            }
            finaList.add(aClass);
        }
        finaList.add(RestController.class);
        finaList.add(RequestMapping.class);
        finaList.add(Autowired.class);
        return finaList;
    }
}
