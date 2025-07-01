package com.example.myforum.dto;

public class ApiResponse {
    private boolean success;
    private String message;
    private int status;

    public ApiResponse(boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
