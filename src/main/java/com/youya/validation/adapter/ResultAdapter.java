package com.youya.validation.adapter;

/**
 * @author tengxq
 */
public class ResultAdapter {

    private String code = "code";

    private String message = "message";

    private Object codeValue = 400;

    public ResultAdapter(String code, String message, Object codeValue) {
        this.code = code;
        this.message = message;
        this.codeValue = codeValue;
    }

    public ResultAdapter() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(Object codeValue) {
        this.codeValue = codeValue;
    }
}
