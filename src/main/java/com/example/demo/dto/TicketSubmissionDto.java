package com.example.demo.dto;

import lombok.Data;

@Data
public class TicketSubmissionDto {
    private String title;
    private String description;
    private String userEmail;
}
