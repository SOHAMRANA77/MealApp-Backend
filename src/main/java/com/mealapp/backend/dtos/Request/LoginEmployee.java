package com.mealapp.backend.dtos.Request;

import lombok.Data;

@Data
public class LoginEmployee {

    private String email;
    private String currentPassword;
}
