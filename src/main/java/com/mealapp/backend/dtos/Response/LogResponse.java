package com.mealapp.backend.dtos.Response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class LogResponse {
    String message;
    HttpStatus http;
    boolean status;

    public LogResponse(String message, HttpStatus http, boolean status) {
        this.message = message;
        this.http = http;
        this.status = status;
    }

    public LogResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return "LogResponse{" +
                "message='" + message + '\'' +
                ", http=" + http +
                ", status=" + status +
                '}';
    }
}
