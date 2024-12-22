package com.jobportal.controller;

import com.jobportal.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class JobPostActivityController {

    private final UserService userService;

    @GetMapping("/dashboard/")
    public String searchJobs(Model model) {
//      Object currentUserProfile = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object currentUserProfile = userService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
        String currentUserName = authentication.getName();
        model.addAttribute("username", currentUserName);
        }
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }
}