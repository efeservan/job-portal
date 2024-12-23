package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name= "job_seeker_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobSeekerProfile {

    @Id
    private Long userAccountId;

    @OneToOne
    @JoinColumn(name="user_account_id")
    @MapsId
    private User userId;

    private String firstName;

    private String lastName;

    private String city;

    private String state;

    private String country;

    private String workAuthorization;

    private String employmentType;

    private String resume;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    @OneToMany(cascade=CascadeType.ALL, targetEntity = Skill.class, mappedBy = "jobSeekerProfile")
    private List<Skill> skills;

    public JobSeekerProfile(User user) {
        this.userId = user;
    }
}
