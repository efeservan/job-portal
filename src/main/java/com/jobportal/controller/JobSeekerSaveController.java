package com.jobportal.controller;

import com.jobportal.entity.JobPostActivity;
import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.JobSeekerSave;
import com.jobportal.entity.User;
import com.jobportal.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class JobSeekerSaveController {

    private final JobPostActivityService jobPostActivityService;
    private final UserService userService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final JobSeekerProfileService jobSeekerProfileService;

    @PostMapping("job-details/save/{id}")
    public String saveJob(@PathVariable("id") Long id, JobSeekerSave jobSeekerSave) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            User user = userService.findByEmail(currentUsername);
            Optional<JobSeekerProfile> jobSeekerProfileOfUser = jobSeekerProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivityOfUser = jobPostActivityService.getOne(id);
            if(jobSeekerProfileOfUser.isPresent() && jobPostActivityOfUser != null){
                jobSeekerSave = new JobSeekerSave();
                jobSeekerSave.setId(id);
                jobSeekerSave.setJob(jobPostActivityOfUser);
                jobSeekerSave.setUserId(jobSeekerProfileOfUser.get()); //wtf
            }else{
                throw new RuntimeException("Job Seeker profile not found");
            }
            jobSeekerSaveService.addNew(jobSeekerSave);
        }
        return "redirect:/dashboard";
    }

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model) {

        List<JobPostActivity> jobPostActivities = new ArrayList<>();
        Object currentUserProfile = userService.getCurrentUserProfile();
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidateJobs((JobSeekerProfile) currentUserProfile);

        for(JobSeekerSave jobSeekerSave : jobSeekerSaveList){
            jobPostActivities.add(jobSeekerSave.getJob());
        }
        model.addAttribute("jobPost", jobPostActivities);
        model.addAttribute("user", currentUserProfile);
        return "saved-jobs";
    }

}
