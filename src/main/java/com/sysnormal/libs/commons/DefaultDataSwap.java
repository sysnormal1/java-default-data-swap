package com.sysnormal.libs.commons;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.Objects;


/**
 * Default class for represent generic data swap between processes.
 *
 * @author aalencarvz1
 * @version 1.0.0
 */
public class DefaultDataSwap {


    /**
     * the success indicative
     */
    public boolean success = false;

    /**
     * the data to swap
     */
    public Object data = null;

    /**
     * the message, if necessary
     */
    public String message = null;

    /**
     * the http status code
     */
    public Integer httpStatusCode = null;

    /**
     * the exception if occurs
     */
    public Exception exception = null;

    public DefaultDataSwap() {}

    public DefaultDataSwap(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public void setException(Exception exception) {
        System.out.println("setting exception");
        if (Objects.nonNull(exception)) {
            exception.printStackTrace();
        }
        this.success = false;
        this.httpStatusCode = Objects.requireNonNullElse(this.httpStatusCode, HttpStatus.INTERNAL_SERVER_ERROR.value());
        this.exception = exception;
        if (!StringUtils.hasText(this.message) && this.exception != null) {
            this.message = this.exception.getMessage();
        }
    }

    public ResponseEntity<DefaultDataSwap> sendHttpResponse() {
        try {
            return this.success ? ResponseEntity.status(HttpStatus.OK).body(this) : ResponseEntity.status(Objects.requireNonNullElse(this.httpStatusCode, HttpStatus.INTERNAL_SERVER_ERROR.value())).body(this);
        } catch (Throwable e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DefaultDataSwap(false, e.getMessage()));
        }
    }
}
