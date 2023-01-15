package com.adem.producer.writer;

import com.adem.common.service.UserService;
import com.adem.producer.writer.model.ControllerResponse;
import com.adem.producer.writer.process.ControllerWriter;
import com.adem.producer.writer.process.impl.ControllerWriterImpl;
import com.adem.producer.writer.process.impl.MappingWriterRestImpl;
import com.adem.producer.writer.util.PropertyReader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Map;

public class CodeWriter {
    public static void main(String[] args) throws Exception {
        new CodeWriter().run();
    }

    public void run() throws Exception {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates");
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
        freeMarkerConfigurer.afterPropertiesSet();
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

        ControllerWriter controllerWriter = new ControllerWriterImpl(new MappingWriterRestImpl());

        Class<?> serviceClass = UserService.class;
        String packageName = "com.example.invoker.controller";


        ControllerResponse response = controllerWriter.processController(serviceClass, packageName, freeMarkerConfigurer.getConfiguration());


        System.out.println(response.getOutput());
        System.out.println("########################################################################");
        for (Map.Entry<Method, String> entry : response.getIgnoreMappingMap().entrySet()) {
            System.out.println("Ignored method:" + entry.getKey().getName() + " \t\t Reason:" + entry.getValue());
        }
        File controllerFile = new File(PropertyReader.getControllerDirectory() + UserService.class.getSimpleName() + "Controller" + ".java");
        try (FileWriter fileWriter = new FileWriter(controllerFile)) {
            fileWriter.write(response.getOutput());
        }

//        Map<String, Object> modelMap = new HashMap<>();
//        modelMap.put("user", new User(1, "adem", "bulut", 3, LocalDate.now()));
//
//        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mapping.ftl");
//
//        StringWriter stringWriter = new StringWriter();
//        template.process(modelMap, stringWriter);
//        String result = stringWriter.toString();
//
//        System.out.println(result);
    }
}
