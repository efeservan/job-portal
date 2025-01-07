package com.jobportal.controller;

import com.jobportal.entity.JobPostActivity;
import com.jobportal.service.JobPostActivityService;
import com.jobportal.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class JobSeekerApplyController {

    private final JobPostActivityService jobPostActivityService;

    private final UserService userService;

    @GetMapping("/job-details-apply/{id}")
    public String display(@PathVariable("id") Long id, Model model) {
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", userService.getCurrentUserProfile());
        return "job-details";
    }


}
