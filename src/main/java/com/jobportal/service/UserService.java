package com.jobportal.service;

import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.RecruiterProfile;
import com.jobportal.entity.User;
import com.jobportal.repository.JobSeekerProfileRepository;
import com.jobportal.repository.RecruiterProfileRepository;
import com.jobportal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public User addNew(User user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        Long userTypeId = user.getUserTypeId().getUserTypeId();
        if(userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return savedUser;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username+" named user " +
                    "not found"));
            Long userId = user.getUserId();
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile(user));
                return recruiterProfile;
            }else{
                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile(user));
                return jobSeekerProfile;  //Constructor ları karsılastır
            }
        }
        return null;
    }
}
