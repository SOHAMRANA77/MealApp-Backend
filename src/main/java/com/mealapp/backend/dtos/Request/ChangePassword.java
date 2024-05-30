package com.mealapp.backend.dtos.Request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class ChangePassword {

    private String email;
    private String newPassword;
    private String oldPassword;

    @Override
    public String toString() {
        return "ChangePassword{" +
                "email='" + email + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }

    public ChangePassword(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

    public ChangePassword(String email, String newPassword, String oldPassword) {
        this.email = email;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }
}
