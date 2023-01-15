package com.adem.producer.writer.process;


import com.adem.producer.writer.model.ControllerResponse;
import freemarker.template.Configuration;

public interface ControllerWriter {
    ControllerResponse processController(Class<?> serviceClass, String packageName, Configuration freemarkerConfiguration);
}
