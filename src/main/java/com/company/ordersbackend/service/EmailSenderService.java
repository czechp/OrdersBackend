package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.List;

@Service
public class EmailSenderService {
    private JavaMailSender javaMailSender;

    @Autowired()
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

    public void sendNotificationAboutNewOrder(String username, String orderName, List<AppUser> users) {
        users.forEach(x -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(x.getEmail());
            simpleMailMessage.setSubject("Nowe zamówienie");
            simpleMailMessage.setText("Nowe zamówienie do realizacji. \n" +
                    "Nazwa zamówienia: " + orderName +
                    "\nZamawiający : " + username);
            new Thread(new EmailSenderThread(javaMailSender, simpleMailMessage)).start();
        });
    }

}
