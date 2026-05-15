package com.example.demo.controller;

import com.example.demo.dto.TicketStatusUpdateDto;
import com.example.demo.models.TicketSubmission;
import com.example.demo.services.TicketStatusTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketStatusTrackingController {

    @Autowired
    private TicketStatusTrackingService statusTrackingService;

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateTicketStatus(@PathVariable Long id, @RequestBody TicketStatusUpdateDto statusUpdateDto) {
        try {
            TicketSubmission updatedTicket = statusTrackingService.updateStatus(id, statusUpdateDto.getStatus());
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
