package com.example.demo;

import com.example.demo.dto.TicketAssignmentDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TechnicianAssignmentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketSubmissionRepository ticketRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAssignTicketToTechnician() throws Exception {
        // 1. Create a ticket
        TicketSubmission ticket = new TicketSubmission();
        ticket.setTitle("Bug Report");
        ticket.setDescription("System crash on login");
        ticket.setUserEmail("user@example.com");
        ticket.setStatus("OPEN");
        ticket = ticketRepository.save(ticket);

        Long ticketId = ticket.getId();

        // 2. Prepare assignment DTO
        TicketAssignmentDto assignmentDto = new TicketAssignmentDto();
        assignmentDto.setTechnicianName("John Doe");

        // 3. Perform POST request to assign ticket
        mockMvc.perform(post("/api/technician/assign/" + ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignmentDto)))
                .andExpect(status().isOk());

        // 4. Verify status is updated to include assignment info
        TicketSubmission assignedTicket = ticketRepository.findById(ticketId).orElseThrow();
        assertEquals("ASSIGNED_TO_JOHN_DOE", assignedTicket.getStatus());
    }
}
