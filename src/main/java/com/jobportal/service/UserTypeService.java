package com.jobportal.service;

import com.jobportal.entity.UserType;
import com.jobportal.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> getAll() {
        return userTypeRepository.findAll();
    }
}
