package com.example.soundcloudfinalprojectittalentss15.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("m.p.galabov@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail sent successfully...");
    }

    public void sendVerificationMail(String toEmail, String subject, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("testsoundclouds15@gmail.com");

    }
}
