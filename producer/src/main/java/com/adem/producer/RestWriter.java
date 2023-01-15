package com.adem.producer;

import com.adem.common.model.AuthenticationUser;
import com.adem.common.service.UserService;
import com.adem.producer.writer.RestControllerWriter;
import com.adem.producer.writer.model.RestWriterConfiguration;

import java.io.IOException;

public class RestWriter {
    public static void main(String[] args) throws IOException {
        RestWriterConfiguration configuration =
                new RestWriterConfiguration.Builder(UserService.class)
                        .authenticationParameter(AuthenticationUser.class)
                        .controllerPackageName("com.adem.producer.controller")
                        .exportJavaFileToPackage(true)
                        .controllerAbsolutePath("/Users/adembulut/projects/java/com.adem.rest/producer/src/main/java/com/adem/producer/controller/")
                        .build();

        RestControllerWriter writer = new RestControllerWriter(configuration);
        try {
            writer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
