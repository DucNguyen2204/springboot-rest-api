package com.example.springbootrestapi.exception;

public class CommentNotMatchPostException extends RuntimeException{
    private String resourceName;

    private String fieldName;

    private String fieldValue;

    private String linkedResourceName;

    private String linkedFieldName;

    private String linkedFieldValue;

    public CommentNotMatchPostException(String resourceName, String fieldName, String fieldValue, String linkedResourceName, String linkedFieldName, String linkedFieldValue) {
        super(String.format("%s with %s %s is not match with %s with %s %s", resourceName,fieldName,fieldValue,linkedResourceName,linkedFieldName,linkedFieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.linkedResourceName = linkedResourceName;
        this.linkedFieldName = linkedFieldName;
        this.linkedFieldValue = linkedFieldValue;
    }

    public CommentNotMatchPostException(String message) {
        super(message);
    }

    public CommentNotMatchPostException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentNotMatchPostException(Throwable cause) {
        super(cause);
    }

    public CommentNotMatchPostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getLinkedResourceName() {
        return linkedResourceName;
    }

    public void setLinkedResourceName(String linkedResourceName) {
        this.linkedResourceName = linkedResourceName;
    }

    public String getLinkedFieldName() {
        return linkedFieldName;
    }

    public void setLinkedFieldName(String linkedFieldName) {
        this.linkedFieldName = linkedFieldName;
    }

    public String getLinkedFieldValue() {
        return linkedFieldValue;
    }

    public void setLinkedFieldValue(String linkedFieldValue) {
        this.linkedFieldValue = linkedFieldValue;
    }
}
