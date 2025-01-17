package com.jobportal.service;

import com.jobportal.entity.JobPostActivity;
import com.jobportal.entity.JobSeekerApply;
import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.repository.JobSeekerApplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobSeekerApplyService {

    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId) {
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
        return jobSeekerApplyRepository.findByJob(job);
    }

    public void addNew(JobSeekerApply jobSeekerApply) {
         jobSeekerApplyRepository.save(jobSeekerApply);
    }
}
