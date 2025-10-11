package com.newProject.first.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailVerificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendVerifyMail(String email, String token) {
        String link = "http://localhost:8080/verify?token="+ token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject("Verify Your Account");
        mail.setText("Click on that link to verify your Account : \n" + link);

        mailSender.send(mail);
    }

}
