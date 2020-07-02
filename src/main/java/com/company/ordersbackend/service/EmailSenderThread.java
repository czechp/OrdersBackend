package com.company.ordersbackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@AllArgsConstructor
@Slf4j
public class EmailSenderThread implements Runnable {
    private JavaMailSender javaMailSender;
    private SimpleMailMessage simpleMailMessage;

    private void send() {
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void run() {
        send();
        log.info("Email with verification token sent");
    }
}
