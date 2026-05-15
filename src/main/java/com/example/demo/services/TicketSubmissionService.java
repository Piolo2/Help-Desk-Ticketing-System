package com.example.demo.services;

import com.example.demo.dto.TicketSubmissionDto;
import com.example.demo.models.TicketSubmission;
import com.example.demo.repository.TicketSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketSubmissionService {

    @Autowired
    private TicketSubmissionRepository ticketRepository;

    public TicketSubmission createTicket(TicketSubmissionDto ticketDto) {
        TicketSubmission ticket = new TicketSubmission();
        ticket.setTitle(ticketDto.getTitle());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setUserEmail(ticketDto.getUserEmail());
        ticket.setStatus("OPEN"); // Default status

        return ticketRepository.save(ticket);
    }
}
