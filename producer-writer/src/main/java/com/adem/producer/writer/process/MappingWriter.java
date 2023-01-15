package com.adem.producer.writer.process;

import com.adem.producer.writer.exception.MethodNotAvailableException;
import freemarker.template.Configuration;

import java.lang.reflect.Method;
import java.util.Set;

public interface MappingWriter {
    String processMapping(Method method, String serviceFieldName, Configuration freemarkerConfiguration, Set<Class<?>> importSet) throws MethodNotAvailableException;
}
