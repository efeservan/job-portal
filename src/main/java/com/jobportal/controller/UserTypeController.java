//package com.newspaper.controller;
//
//import com.newspaper.entity.UserType;
//import com.newspaper.repository.UserTypeRepository;
//import com.newspaper.service.UserTypeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import java.util.List;
//
//@Controller
//public class UserTypeController {
//
//    private final UserTypeService usersTypeService;
//    private final UserTypeRepository usersTypeRepository;
//
//    @Autowired
//    public UserTypeController(UserTypeService usersTypeService, UserTypeRepository usersTypeRepository) {
//        this.usersTypeService = usersTypeService;
//        this.usersTypeRepository = usersTypeRepository;
//    }
//
//    public List<UserType> getAllUserTypes() {
//        return usersTypeRepository.findAll();
//    }
//}
