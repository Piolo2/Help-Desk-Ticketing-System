package com.example.demo.services;

import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService implements NotificationService {

    @Override
    public void sendAlert(String phoneNumber, String message) {
        // Logic to send SMS (e.g., using Twilio)
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}
