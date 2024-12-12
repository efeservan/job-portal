package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Table(name="skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String experienceLevel;

    private String yearsOfExperience;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile")
    private JobSeekerProfile jobSeekerProfile;

}
