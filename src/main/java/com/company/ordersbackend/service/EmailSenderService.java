package com.company.ordersbackend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

@Service
public class EmailSenderService {
    private JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationToken(String email, String token, ServletRequest servletRequest) {
        String content = new StringBuilder()
                .append("Aby aktywować konto kliknij w link ---->  ")
                .append("http://")
                .append(servletRequest.getServerName())
                .append(":")
                .append(servletRequest.getServerPort())
                .append("/api/activate-user?token=")
                .append(token)
                .toString();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Zamównienia automatycy  - aktywacja konta");
        simpleMailMessage.setText(content);

        new Thread(new EmailSenderThread(javaMailSender, simpleMailMessage)).start();

    }

}
