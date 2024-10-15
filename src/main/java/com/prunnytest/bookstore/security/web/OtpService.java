package com.prunnytest.bookstore.security.web;

import com.prunnytest.bookstore.model.Otp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
@RequiredArgsConstructor
@Service
public class OtpService {
    private final JavaMailSender jms;
    @Value("${spring.mail.username}") private String sender;

    public void sendOTP(String email){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(email);
            message.setText("from HEUY: your otp code is: " + generateOTP());
            message.setSubject("OTP");
            jms.send(message);
        }catch(Exception e) {
            e.getMessage();
        }

    }
    private String generateOTP(){
        String numbers = "0123456789";
        StringBuilder otp = new StringBuilder(5);
        Random random = new Random();

        for(int i = 0; i < 5; i++){
            otp.append(numbers.charAt((random.nextInt(10))));
        }
        return otp.toString();
    }

}

