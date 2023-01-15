package com.adem.producer.writer;

import com.adem.common.service.UserService;
import com.adem.producer.writer.model.ControllerResponse;
import com.adem.producer.writer.model.RestWriterConfiguration;
import com.adem.producer.writer.process.ControllerWriter;
import com.adem.producer.writer.process.impl.ControllerWriterImpl;
import com.adem.producer.writer.process.impl.MappingWriterImpl;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.UnexpectedException;
import java.util.Map;

public class RestControllerWriter {
    private final RestWriterConfiguration restWriterConfiguration;

    public RestControllerWriter(RestWriterConfiguration restWriterConfiguration) {
        this.restWriterConfiguration = restWriterConfiguration;
    }

    public void run() throws IOException {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates");
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
        try {
            freeMarkerConfigurer.afterPropertiesSet();
        } catch (TemplateException e) {
            throw new UnexpectedException("Freemarker configuration not initialized!");
        }
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

        ControllerWriter controllerWriter = new ControllerWriterImpl(restWriterConfiguration, new MappingWriterImpl(restWriterConfiguration));

        Class<?> serviceClass = UserService.class;
        String packageName = restWriterConfiguration.getControllerPackageName();


        ControllerResponse response = controllerWriter.processController(serviceClass, packageName, freeMarkerConfigurer.getConfiguration());


        System.out.println(response.getOutput());
        System.out.println("########################################################################");
        for (Map.Entry<Method, String> entry : response.getIgnoreMappingMap().entrySet()) {
            System.out.println("Ignored method:" + entry.getKey().getName() + " \t\t Reason:" + entry.getValue());
        }
        File controllerFile = new File(restWriterConfiguration.getControllerAbsolutePath() + restWriterConfiguration.getServiceClass().getSimpleName() + "Controller" + ".java");
        try (FileWriter fileWriter = new FileWriter(controllerFile)) {
            fileWriter.write(response.getOutput());
        }
    }
}
