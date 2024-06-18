package com.mealapp.backend.dtos.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class ChangePassword {

    @Schema(description = "Email of the user whose password is being changed")
    private String email;

    @Schema(description = "New password to set for the user")
    private String newPassword;

    @Schema(description = "Current password for verification (optional)")
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
