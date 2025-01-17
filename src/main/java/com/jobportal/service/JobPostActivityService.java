package com.jobportal.service;

import com.jobportal.dto.RecruiterJobsDto;
import com.jobportal.entity.IRecruiterJobs;
import com.jobportal.entity.JobCompany;
import com.jobportal.entity.JobLocation;
import com.jobportal.entity.JobPostActivity;
import com.jobportal.repository.JobPostActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(Long recruiter) {
        List<IRecruiterJobs> recruiterJobDtos = jobPostActivityRepository.getRecruiterJobs(recruiter);

        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();

        for (IRecruiterJobs rec : recruiterJobDtos) {
            JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
            JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
            recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJob_post_id(), rec.getJob_title(), loc, comp));
        }
        return recruiterJobsDtoList;
    }

    public JobPostActivity getOne(Long id) {
        return jobPostActivityRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found with the : " + id + "id"));
    }

    public List<JobPostActivity> getAll() {
        return jobPostActivityRepository.findAll();
    }

    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {
        return Objects.isNull(searchDate) ? jobPostActivityRepository.searchWithoutDate(job, location, type, remote) :
                jobPostActivityRepository.search(job, location, type, remote, searchDate);
    }
}
