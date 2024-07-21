package com.example.demo.Interfaces;

import org.springframework.mail.SimpleMailMessage;

public interface IMailService {
     void sendEmail(SimpleMailMessage email);
}
