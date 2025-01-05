package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class JobSeekerApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apply_date", nullable = false)
    private LocalDateTime applyDate;

    private String coverLetter;

    @ManyToOne
    @JoinColumn(name = "job", nullable = false)
    private JobPostActivity job;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private JobSeekerProfile userId;
}
