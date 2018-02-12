package com.bh.tools.http.model;

import java.io.Serializable;

/**
 * @author JanChao .
 */
public class Response implements Serializable {

    private int code;
    private String errorMessage;
    private String content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
