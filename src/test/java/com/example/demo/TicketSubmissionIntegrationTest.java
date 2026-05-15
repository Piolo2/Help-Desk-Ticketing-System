package com.example.demo;

import com.example.demo.dto.TicketSubmissionDto;
import com.example.demo.repository.TicketSubmissionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketSubmissionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketSubmissionRepository ticketRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSubmitTicket() throws Exception {
        // Create a new ticket DTO
        TicketSubmissionDto ticketDto = new TicketSubmissionDto();
        ticketDto.setTitle("Network Issue");
        ticketDto.setDescription("Cannot connect to wifi");
        ticketDto.setUserEmail("student@gmail.com");

        // Perform POST request
        mockMvc.perform(post("/api/tickets/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isCreated());

        // Verify ticket is in the database
        long count = ticketRepository.count();
        assertTrue(count > 0, "Ticket should be saved in the database");
    }
}
