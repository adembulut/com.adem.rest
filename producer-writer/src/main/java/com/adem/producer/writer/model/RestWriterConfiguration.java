package com.adem.producer.writer.model;

public class RestWriterConfiguration {
    private final Class<?> serviceClass;
    private final Class<?> authenticationParameter;
    private final String controllerPackageName;
    private final boolean exportJavaFileToPackage;
    private final String controllerPath;


    public RestWriterConfiguration(Builder builder) {
        this.serviceClass = builder.serviceClass;
        this.authenticationParameter = builder.authenticationParameter;
        this.controllerPackageName = builder.controllerPackageName;
        this.controllerPath = builder.controllerPath;
        this.exportJavaFileToPackage = builder.exportJavaFileToPackage;
    }

    public static class Builder {
        private final Class<?> serviceClass;
        private Class<?> authenticationParameter;
        private String controllerPackageName;
        private boolean exportJavaFileToPackage;
        private String controllerPath;

        public Builder(Class<?> serviceClass) {
            this.serviceClass = serviceClass;
        }

        public Builder authenticationParameter(Class<?> authenticationParameter) {
            this.authenticationParameter = authenticationParameter;
            return this;
        }

        public Builder controllerPackageName(String controllerPackageName) {
            this.controllerPackageName = controllerPackageName;
            return this;
        }

        public Builder controllerAbsolutePath(String controllerAbsolutePath) {
            this.controllerPath = controllerAbsolutePath;
            return this;
        }

        public Builder exportJavaFileToPackage(boolean exportJavaFileToPackage) {
            this.exportJavaFileToPackage = exportJavaFileToPackage;
            return this;
        }

        public RestWriterConfiguration build() {
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

    public String getControllerPath() {
        return controllerPath;
    }

    public boolean isExportJavaFileToPackage() {
        return exportJavaFileToPackage;
    }
}
