package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * here is the methode to get login template
 */

@Controller
@SessionAttributes("userInfo")
@RequestMapping("app")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * this method return the login template
     *
     * @return the login template
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }



}
