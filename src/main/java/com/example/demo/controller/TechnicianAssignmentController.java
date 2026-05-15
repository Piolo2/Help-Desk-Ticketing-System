package com.example.demo.controller;

import com.example.demo.dto.TicketAssignmentDto;
import com.example.demo.models.TicketSubmission;
import com.example.demo.services.TechnicianAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/technician")
public class TechnicianAssignmentController {

    @Autowired
    private TechnicianAssignmentService assignmentService;

    @PostMapping("/assign/{id}")
    public ResponseEntity<?> assignTicket(@PathVariable Long id, @RequestBody TicketAssignmentDto assignmentDto) {
        try {
            TicketSubmission assignedTicket = assignmentService.assignTicket(id, assignmentDto.getTechnicianName());
            return new ResponseEntity<>(assignedTicket, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
