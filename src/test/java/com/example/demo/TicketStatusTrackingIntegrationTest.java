package com.example.demo;

import com.example.demo.dto.TicketStatusUpdateDto;
import com.example.demo.dto.TicketSubmissionDto;
import com.example.demo.models.TicketSubmission;
import com.example.demo.repository.TicketSubmissionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketStatusTrackingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketSubmissionRepository ticketRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUpdateTicketStatus() throws Exception {
        // 1. Create a ticket first
        TicketSubmission ticket = new TicketSubmission();
        ticket.setTitle("Test Ticket");
        ticket.setDescription("Testing status update");
        ticket.setUserEmail("test@example.com");
        ticket.setStatus("OPEN");
        ticket = ticketRepository.save(ticket);

        Long ticketId = ticket.getId();

        // 2. Prepare status update DTO
        TicketStatusUpdateDto statusUpdateDto = new TicketStatusUpdateDto();
        statusUpdateDto.setStatus("IN_PROGRESS");

        // 3. Perform PATCH request
        mockMvc.perform(patch("/api/tickets/" + ticketId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusUpdateDto)))
                .andExpect(status().isOk());

        // 4. Verify status is updated in the database
        TicketSubmission updatedTicket = ticketRepository.findById(ticketId).orElseThrow();
        assertEquals("IN_PROGRESS", updatedTicket.getStatus());
    }
}
