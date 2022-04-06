package com.nnk.springboot.controllers;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes("userInfo")
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger("RegistrationController");

    @GetMapping("/registrationForm")
    public String registrationForm(User user){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid User user, BindingResult result, Model model){
        if (userRepository.findByUsername(user.getUsername()) != null){
            model.addAttribute("userError", "Username already used");
            logger.error("An error occurred during user registration: Username already used");
            return "registration";
        }
        if(!result.hasErrors()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setEnabled(true);
            userRepository.save(user);
            return "redirect:app/login";
        }
        return "registration";
    }
}
