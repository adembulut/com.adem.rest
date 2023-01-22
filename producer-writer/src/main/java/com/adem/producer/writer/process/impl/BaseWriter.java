package com.adem.producer.writer.process.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseWriter {
    private final Set<String> classDefaultMethods =new HashSet<>(Arrays.asList("hashCode","toString","equals","wait","notify","notifyAll","getClass"));
    private final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>() {
        private static final long serialVersionUID = 5851119397623390011L;

        {
            put(boolean.class, Boolean.class);
            put(char.class, Character.class);
            put(byte.class, Byte.class);
            put(short.class, Short.class);
            put(int.class, Integer.class);
            put(long.class, Long.class);
            put(float.class, Float.class);
            put(double.class, Double.class);
            put(void.class, Void.class);
        }
    };

    public boolean isDefaultMethod(Method method){
        if(method==null){
            return false;
        }
        return classDefaultMethods.contains(method.getName());
    }

    public String getFieldName(String name) {
        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException("Name must not be null");
        }
        StringBuilder builder = new StringBuilder();
        builder.append(Character.toLowerCase(name.charAt(0)));
        if (name.length() > 1) {
            builder.append(name.substring(1));
        }
        return builder.toString();
    }

    public String getUrlName(String name) {
        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        char[] chars = name.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char myChar = chars[i];
            if (i != 0) {
                if (Character.isUpperCase(myChar)) {
                    builder.append("-");
                }
            }
            builder.append(Character.toLowerCase(myChar));
        }
        return builder.toString();
    }

    public boolean isJavaObject(Class<?> zClass) {
        if (zClass == null) {
            throw new RuntimeException("Class must not be null!");
        }
        if (zClass.isPrimitive()) {
            return true;
        }
        return zClass.getPackageName().startsWith("java.lang");
    }

    public boolean isPrimitive(Class<?> zClass) {
        if (zClass == null) {
            throw new RuntimeException("Class must not be null!");
        }
        return primitiveWrapperMap.containsKey(zClass);
    }

    public Class<?> getWrapperClass(Class<?> zClass) {
        return primitiveWrapperMap.get(zClass);
    }

    public void addClassToCollection(Class<?> zClass, Collection<Class<?>> collection){
        if(zClass==null || collection==null){
            return;
        }

        collection.add(zClass);
    }
    public void addParameterToClassCollection(Parameter parameter, Collection<Class<?>> collection){
        if(parameter==null || collection==null){
            return;
        }

        collection.add(parameter.getType());
    }

    public void addTypeToClassCollection(Type type, Collection<Class<?>> collection){
        if(type==null || collection==null){
            return;
        }

        collection.add(type.getClass());
    }
}
