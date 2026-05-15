package com.example.demo.services;

import com.example.demo.models.TicketSubmission;
import com.example.demo.repository.TicketSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketStatusTrackingService {

    @Autowired
    private TicketSubmissionRepository ticketRepository;

    public TicketSubmission updateStatus(Long id, String status) {
        Optional<TicketSubmission> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            TicketSubmission ticket = optionalTicket.get();
            ticket.setStatus(status);
            return ticketRepository.save(ticket);
        } else {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
    }
}
