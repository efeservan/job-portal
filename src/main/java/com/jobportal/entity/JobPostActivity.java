package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobPostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_post_id")
    private Long jobPostId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "postedById", referencedColumnName = "userId")
    private User postedById;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="jobLocationId", referencedColumnName = "id")
    private JobLocation jobLocationId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobCompanyId", referencedColumnName = "id")
    private JobCompany jobCompanyId;

    @Transient
    private boolean isActive;

    @Transient
    private boolean isSaved;

    @Length(max = 1000)
    private String descriptionOfJob;

    private String jobType;

    private String salary;

    private String remote;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date postedDate;

    private String jobTitle;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public boolean getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean saved) {
        isSaved = saved;
    }
}
