package com.example.myforum.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil {
    // Gửi email đơn giản (cần cấu hình JavaMailSender ở Spring context)
    public static void sendEmail(JavaMailSender mailSender, String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // true = gửi HTML
        mailSender.send(message);
    }
}
