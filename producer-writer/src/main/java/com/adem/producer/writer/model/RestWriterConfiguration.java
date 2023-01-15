package com.adem.producer.writer.model;

public class RestWriterConfiguration {
    private final Class<?> serviceClass;
    private final Class<?> authenticationParameter;
    private final String controllerPackageName;
    private final String controllerAbsolutePath;


    public RestWriterConfiguration(Builder builder) {
        this.serviceClass = builder.serviceClass;
        this.authenticationParameter = builder.authenticationParameter;
        this.controllerPackageName = builder.controllerPackageName;
        this.controllerAbsolutePath = builder.controllerAbsolutePath;
    }

    public static class Builder{
        private final Class<?> serviceClass;
        private Class<?> authenticationParameter;
        private String controllerPackageName;
        private String controllerAbsolutePath;

        public Builder(Class<?> serviceClass) {
            this.serviceClass = serviceClass;
        }

        public Builder authenticationParameter(Class<?> authenticationParameter){
            this.authenticationParameter = authenticationParameter;
            return this;
        }

        public Builder controllerPackageName(String controllerPackageName){
            this.controllerPackageName = controllerPackageName;
            return this;
        }

        public Builder controllerAbsolutePath(String controllerAbsolutePath){
            this.controllerAbsolutePath=controllerAbsolutePath;
            return this;
        }

        public RestWriterConfiguration build(){
            return new RestWriterConfiguration(this);
        }
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

    public Class<?> getAuthenticationParameter() {
        return authenticationParameter;
    }

    public String getControllerPackageName() {
        return controllerPackageName;
    }

    public String getControllerAbsolutePath() {
        return controllerAbsolutePath;
    }
}
