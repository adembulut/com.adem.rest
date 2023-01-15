package com.adem.producer.writer.process.impl;

public abstract class BaseWriter {

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
}
