package com.adem.producer.writer;

import com.adem.common.model.User;
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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.UnexpectedException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class RestControllerWriter {
    private final RestWriterConfiguration writerConf;

    public RestControllerWriter(RestWriterConfiguration writerConf) throws IOException{
        validateConfiguration(writerConf);
        this.writerConf = writerConf;
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

        ControllerWriter controllerWriter = new ControllerWriterImpl(writerConf, new MappingWriterImpl(writerConf));

        Class<?> serviceClass = UserService.class;
        String packageName = writerConf.getControllerPackageName();


        ControllerResponse response = controllerWriter.processController(serviceClass, packageName, freeMarkerConfigurer.getConfiguration());


        System.out.println(response.getOutput());
        System.out.println("########################################################################");
        for (Map.Entry<Method, String> entry : response.getIgnoreMappingMap().entrySet()) {
            System.out.println("Ignored method:" + entry.getKey().getName() + " \t\t Reason:" + entry.getValue());
        }


        if (writerConf.isExportJavaFileToPackage()) {
            File controllerFile = createControllerFile();
            try (FileWriter fileWriter = new FileWriter(controllerFile)) {
                fileWriter.write(response.getOutput());
            }
        }
    }


    private File createControllerFile() {
        return new File(writerConf.getControllerPath() + writerConf.getServiceClass().getSimpleName() + "Controller.java");
    }


    private void validateConfiguration(RestWriterConfiguration configuration) throws IOException{
        if (configuration == null) {
            throw new NullPointerException("Configuration must not be null");
        }
        validateServiceClass(configuration.getServiceClass());
        validateExportStatus(configuration.isExportJavaFileToPackage(),configuration.getControllerPath());
    }

    private static void validateExportStatus(boolean isExportJavaFileToPackage,String controllerPath) throws IOException{
        if(!isExportJavaFileToPackage){
            return;
        }
        File file = new File(controllerPath);
        if(!file.exists()){
            throw new FileNotFoundException(controllerPath+" not found or accessible!");
        }
    }

    private static void validateServiceClass(Class<?> serviceClass) {
        if (serviceClass == null) {
            throw new NullPointerException("Service class must not be null");
        }
        if (serviceClass.isPrimitive()) {
            throw new IllegalArgumentException("Service class must not be primitive");
        }
        if (serviceClass.isAnnotation()) {
            throw new IllegalArgumentException("Service class must not be annotation");
        }
        if (serviceClass.isEnum()) {
            throw new IllegalArgumentException("Service class must not be enum");
        }
        if(serviceClass.isArray()){
            throw new IllegalArgumentException("Service class must not be array");
        }
    }

    public static void main(String[] args) {
        validateServiceClass(User.class);
        validateServiceClass(List.class);
    }
}
