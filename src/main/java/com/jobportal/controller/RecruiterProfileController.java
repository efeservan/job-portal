package com.jobportal.controller;

import com.jobportal.entity.RecruiterProfile;
import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.RecruiterProfileService;
import com.jobportal.util.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final RecruiterProfileService recruiterProfileService;
    private final UserRepository userRepository;

    public RecruiterProfileController(RecruiterProfileService recruiterProfileService, UserRepository userRepository) {
        this.recruiterProfileService = recruiterProfileService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName).orElseThrow(
                    () -> new UsernameNotFoundException("User Not Found: " + currentUserName));
            Optional<RecruiterProfile> recruiterProfile =
                    recruiterProfileService.getOne(user.getUserId());
            if (!recruiterProfile.isEmpty()) {
                model.addAttribute("profile", recruiterProfile.get());
            }
        }
        return "recruiter_profile";
    }

    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile multipartfile,
                         Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName).orElseThrow(
                    () -> new UsernameNotFoundException("User Not Found: " + currentUserName));
            recruiterProfile.setUserId(user);
            recruiterProfile.setUserAccountId(user.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        String filename = "";
        if (!multipartfile.getOriginalFilename().equals("")) {
            filename = StringUtils.cleanPath(Objects.requireNonNull(multipartfile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(filename);
        }
        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
        String uploadDir = "photos/recruiter/" + savedUser.getUserAccountId();
        try {
            FileUploadUtil.saveFile(uploadDir, filename, multipartfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/dashboard/";
    }
}
