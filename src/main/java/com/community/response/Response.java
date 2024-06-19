package com.community.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response<T> {
    private final int status;
    private final String message;
    private final T data;

    public Response(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static ResponseEntity<?> createResponse(HttpStatus status, String message, Object data) {
        return ResponseEntity.status(status).body(new Response<>(status.value(), message, data));
    }
}
