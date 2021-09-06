package com.example.myapplication.manager.api;

public enum ContentType {
    TEXT_PLAIN("text/plain"),
    APPLICATION_JSON("application/json; charset=utf-8"),
    APPLICATION_X_WWW_FORM_URL_ENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data");

    private String mTypeName;

    ContentType(String typeName) {
        mTypeName = typeName;
    }

    public String getTypeName() {
        return mTypeName;
    }

    public static ContentType valueof(String typeName) {
        for (ContentType contentType : values()) {
            if (contentType.getTypeName().equals(typeName)) {
                return contentType;
            }
        }
        return null;
    }
}
