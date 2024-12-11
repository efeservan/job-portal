package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.entity.UserType;
import com.jobportal.service.UserService;
import com.jobportal.service.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserTypeService userTypeService;
    private final UserService userService;

    @Autowired
    public UserController(UserTypeService userTypeService, UserService userService) {
        this.userTypeService = userTypeService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UserType> userTypes = userTypeService.getAll();
        System.out.println("User Types: " + userTypes);
        model.addAttribute("getAllTypes", userTypes);
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid User user, Model model) {
        Optional<User> optionalUser = userService.getUserByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            model.addAttribute("error", "Email is already in use");
            List<UserType> userTypes = userTypeService.getAll();
            model.addAttribute("getAllTypes", userTypes);
            model.addAttribute("user", new User());
            return "register";
        }
        else {
            userService.addNew(user);
            return "dashboard";
        }
    }
}
