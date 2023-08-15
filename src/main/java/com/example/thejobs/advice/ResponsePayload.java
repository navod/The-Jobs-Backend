package com.example.thejobs.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class ResponsePayload implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private HttpStatus status;
    private transient Object data;
    private String message;

    public ResponsePayload(String message, Object object, HttpStatus httpStatus) {
        this.status = httpStatus;
        this.data = object;
        this.message = message;

    }

    public ResponsePayload() {

    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
