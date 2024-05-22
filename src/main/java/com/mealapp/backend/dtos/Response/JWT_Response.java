package com.mealapp.backend.dtos.Response;

import com.mealapp.backend.enums.UserRole;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class JWT_Response {
    String message;
    HttpStatus http;
    boolean status;
    private String jwt;
    private UserRole userRole;
    private Long Id;

    public JWT_Response(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public JWT_Response(String message, HttpStatus http, boolean status, String jwt, UserRole userRole, Long id) {
        this.message = message;
        this.http = http;
        this.status = status;
        this.jwt = jwt;
        this.userRole = userRole;
        Id = id;
    }

    public JWT_Response() {
    }
}
