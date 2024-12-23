package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name= "recruiter_profile")
@Data
@ToString
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAccountId;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private User userId;

    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String country;
    private String company;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    public RecruiterProfile() {
    }

    public RecruiterProfile(Long userAccountId, User userId, String firstName, String lastName, String city, String state, String country, String company, String profilePhoto) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.company = company;
        this.profilePhoto = profilePhoto;
    }

    public RecruiterProfile(User user) {
        this.userId = user;
    }
}
