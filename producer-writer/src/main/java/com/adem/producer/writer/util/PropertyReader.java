package com.adem.producer.writer.util;


import com.adem.common.model.AuthenticationUser;
import com.adem.common.service.UserService;

public class PropertyReader {

    public static Class<?> getServiceClass() {
        return UserService.class;
    }

    public static Class<?> getLoggedParameter() {
        return AuthenticationUser.class;
    }

    public static boolean existLoggedParameter() {
        return true;
    }

    public static String getControllerDirectory() {
        return "/Users/adembulut/projects/java/com.adem.rest.writer/src/main/java/com/adem/rest/writer/controller/";
    }


}
