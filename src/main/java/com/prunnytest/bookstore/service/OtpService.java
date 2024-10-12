package com.prunnytest.bookstore.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@NoArgsConstructor
public class OtpService {
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
