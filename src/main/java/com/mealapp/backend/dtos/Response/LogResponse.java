package com.mealapp.backend.dtos.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class LogResponse {

    @Schema(description = "Message describing the result of the operation")
    private String message;

    @Schema(description = "HTTP status code returned by the operation")
    private HttpStatus http;

    @Schema(description = "Boolean status indicating success or failure of the operation")
    private boolean status;

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
