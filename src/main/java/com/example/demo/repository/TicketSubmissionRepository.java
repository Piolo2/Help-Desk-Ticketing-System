package com.example.demo.repository;

import com.example.demo.models.TicketSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSubmissionRepository extends JpaRepository<TicketSubmission, Long> {
}
