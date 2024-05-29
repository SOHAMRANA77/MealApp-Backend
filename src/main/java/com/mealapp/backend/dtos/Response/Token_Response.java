package com.mealapp.backend.dtos.Response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Token_Response {

    String message;
    HttpStatus http;
    boolean status;
    private String jwt;

    public Token_Response() {
    }

    public Token_Response(String message, HttpStatus http, boolean status, String jwt) {
        this.message = message;
        this.http = http;
        this.status = status;
        this.jwt = jwt;
    }

    public Token_Response(String message, HttpStatus http, boolean status) {
        this.message = message;
        this.http = http;
        this.status = status;
    }

    public Token_Response(HttpStatus http, boolean status, String jwt) {
        this.http = http;
        this.status = status;
        this.jwt = jwt;
    }
}
