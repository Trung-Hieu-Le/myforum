package com.example.myforum.util;

public class ValidationUtil {
    // Kiểm tra email hợp lệ
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }

    // Kiểm tra số điện thoại Việt Nam hợp lệ
    public static boolean isValidVietnamPhone(String phone) {
        return phone != null && phone.matches("^(0|\\+84)[1-9][0-9]{8}$");
    }

    // Kiểm tra password đủ mạnh (ít nhất 8 ký tự, có chữ và số)
    public static boolean isStrongPassword(String password) {
        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
}
