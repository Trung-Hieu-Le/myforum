package com.example.myforum.util;

public class StringUtil {
    // Loại bỏ khoảng trắng đầu cuối và chuẩn hóa nhiều khoảng trắng thành 1
    public static String normalizeWhitespace(String input) {
        return input == null ? null : input.trim().replaceAll("\\s+", " ");
    }

    // Kiểm tra chuỗi có phải là email hợp lệ
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }

    // Rút gọn nội dung dài (ví dụ cho preview bài viết)
    public static String truncate(String input, int maxLength) {
        if (input == null || input.length() <= maxLength) return input;
        return input.substring(0, maxLength) + "...";
    }
}
