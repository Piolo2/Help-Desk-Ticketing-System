package com.example.demo.services;

import com.example.demo.dto.TicketSubmissionDto;
import com.example.demo.models.TicketSubmission;
import com.example.demo.repository.TicketSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TicketSubmissionService {

    @Autowired
    private TicketSubmissionRepository ticketRepository;

    @Autowired
    @Qualifier("emailNotificationService")
    private NotificationService emailService;

    @Autowired
    @Qualifier("smsNotificationService")
    private NotificationService smsService;

    public TicketSubmission createTicket(TicketSubmissionDto ticketDto) {
        TicketSubmission ticket = new TicketSubmission();
        ticket.setTitle(ticketDto.getTitle());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setUserEmail(ticketDto.getUserEmail());
        ticket.setStatus("OPEN"); // Default status

        TicketSubmission savedTicket = ticketRepository.save(ticket);
        
        // Trigger email notification
        if (ticketDto.getUserEmail() != null && !ticketDto.getUserEmail().isEmpty()) {
            emailService.sendAlert(
                ticketDto.getUserEmail(), 
                "Your ticket '" + ticket.getTitle() + "' has been created successfully."
            );
        }
        
        return savedTicket;
    }

    public TicketSubmission resolveTicket(Long id) {
        Optional<TicketSubmission> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            TicketSubmission ticket = optionalTicket.get();
            ticket.setStatus("RESOLVED");
            TicketSubmission updatedTicket = ticketRepository.save(ticket);

            // Trigger "User notified" alert
            if (ticket.getUserEmail() != null && !ticket.getUserEmail().isEmpty()) {
                emailService.sendAlert(
                    ticket.getUserEmail(), 
                    "Update: Your ticket '" + ticket.getTitle() + "' has been resolved!"
                );
            }
            
            return updatedTicket;
        } else {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
    }

    public java.util.List<TicketSubmission> getAllTickets() {
        return ticketRepository.findAll();
    }
}
