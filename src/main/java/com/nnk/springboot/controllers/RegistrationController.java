package com.nnk.springboot.controllers;


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

/**
 * here are the methods to registration
 */

@Controller
@SessionAttributes("userInfo")
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger("RegistrationController");


    /**
     * this method return the registration template
     *
     * @return the registration template
     */
    @GetMapping("/registrationForm")
    public String registrationForm(User user){
        return "registration";
    }


    /**
     * this method allows you to register a new user
     *
     * @param user a valid user
     * @return the template connect if the user in parameter is valid or
     * @return the template to add register user if the user as error
     */
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
