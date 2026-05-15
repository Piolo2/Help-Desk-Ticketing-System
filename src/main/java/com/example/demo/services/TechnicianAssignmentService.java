package com.example.demo.services;

import com.example.demo.models.TicketSubmission;
import com.example.demo.repository.TicketSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TechnicianAssignmentService {

    @Autowired
    private TicketSubmissionRepository ticketRepository;

    public TicketSubmission assignTicket(Long ticketId, String technicianName) {
        Optional<TicketSubmission> optionalTicket = ticketRepository.findById(ticketId);
        if (optionalTicket.isPresent()) {
            TicketSubmission ticket = optionalTicket.get();
            // Since we cannot modify the model to add a technician field, 
            // we will update the status to indicate assignment for now.
            // If the model had a technician field, we would set it here.
            ticket.setStatus("ASSIGNED_TO_" + technicianName.toUpperCase().replace(" ", "_"));
            return ticketRepository.save(ticket);
        } else {
            throw new RuntimeException("Ticket not found with id: " + ticketId);
        }
    }
}
