package com.jobportal.service;

import com.jobportal.entity.RecruiterProfile;
import com.jobportal.entity.User;
import com.jobportal.repository.RecruiterProfileRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository, UserRepository userRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.userRepository = userRepository;
    }

    public Optional<RecruiterProfile> getOne(Long userAccountId) {
        return recruiterProfileRepository.findById(userAccountId);
    }

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }

    public RecruiterProfile getCurrentRecruiterProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            Optional<RecruiterProfile> recruiterProfile = this.getOne(user.getUserId());
            return recruiterProfile.orElse(null);
        } else
            return null;
    }
}
