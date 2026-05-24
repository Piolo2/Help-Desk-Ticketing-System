package com.example.demo.controller;

import com.example.demo.dto.TicketSubmissionDto;
import com.example.demo.models.TicketSubmission;
import com.example.demo.services.TicketSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketSubmissionController {

    @Autowired
    private TicketSubmissionService ticketService;

    @PostMapping("/")
    public ResponseEntity<String> submitTicket(@RequestBody TicketSubmissionDto ticketDto) {
        ticketService.createTicket(ticketDto);
        return new ResponseEntity<>("Ticket Submitted Successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<String> resolveTicket(@PathVariable Long id) {
        try {
            ticketService.resolveTicket(id);
            return new ResponseEntity<>("Ticket Resolved and User Notified Successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<java.util.List<TicketSubmission>> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }
}
