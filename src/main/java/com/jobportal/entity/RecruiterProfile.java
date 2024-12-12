package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name= "recruiter_profile")
@Data
@ToString
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruiterProfileId;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_account_id")
    @MapsId
    private User userId;

    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String country;
    private String company;

    @Column(length=64, nullable=true)
    private String profilePhotoUrl;

    public RecruiterProfile(User user) {
        this.userId = user;
    }
}
