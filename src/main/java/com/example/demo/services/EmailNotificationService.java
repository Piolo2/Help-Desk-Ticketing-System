package com.example.demo.services;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {

    @Override
    public void sendAlert(String emailAddress, String message) {
        // Logic to send email (e.g., using JavaMailSender)
        System.out.println("Sending Email to " + emailAddress + ": " + message);
    }
}
