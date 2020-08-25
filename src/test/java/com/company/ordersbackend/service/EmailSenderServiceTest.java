package com.company.ordersbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@RunWith(SpringRunner.class)
class EmailSenderServiceTest {

    private EmailSenderService emailSenderService;

    @Mock()
    JavaMailSender javaMailSender;



    @BeforeEach()
    public void init(){
        this.emailSenderService = new EmailSenderService(javaMailSender);
    }





}