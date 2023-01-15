package com.adem.producer.writer.process;

import com.adem.producer.writer.exception.MethodNotAvailableException;
import com.adem.producer.writer.model.MethodModel;
import freemarker.template.Configuration;

import java.lang.reflect.Method;
import java.util.Set;

public interface MappingWriter {
    MethodModel processMapping(Method method, String serviceFieldName, Configuration freemarkerConfiguration) throws MethodNotAvailableException;
}
