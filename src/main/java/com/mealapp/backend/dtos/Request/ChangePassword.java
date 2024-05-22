package com.mealapp.backend.dtos.Request;

public class ChangePassword {

    private String email;
    private String newPassword;

    @Override
    public String toString() {
        return "ChangePassword{" +
                "email='" + email + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }

    public ChangePassword() {
    }

    public ChangePassword(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
