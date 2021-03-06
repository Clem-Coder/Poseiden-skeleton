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
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

/**
 * here is the CRUD for User entities
 */

@Controller
@SessionAttributes("userInfo")
public class UserController {

    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private UserRepository userRepository;

    /**
     * Show the template to list users
     *
     * @return the userList template
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        logger.info("New request: show all users in the view ");
        return "user/list";
    }

    /**
     * Show the template to add new user
     *
     * @return the template to add new user
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        logger.info("New request: show form to add new user in the view ");
        return "user/add";
    }

    /**
     * this method allows you to add a new user
     *
     * @param user a valid user
     * @return the template to list user if the user in parameter is valid or
     * @return the template to add new user if the user as error
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (userRepository.findByUsername(user.getUsername()) != null){
            model.addAttribute("userError", "Username already used");
            logger.error("An error occurred during user registration: Username already used");
            return "user/add";
        }
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setEnabled(true);
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            logger.info("New request: user added in db ");
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * Show the template to update a user
     *
     * @param id the id of the user to update
     * @return the template to update user
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        logger.info("New request: show form to update user in the view ");
        return "user/update";
    }

    /**
     * this method allows you to update a user
     *
     * @param id the id of the user to update
     * @return the template to list user if the user in parameter is valid or
     * @return the template to update user if the user as error
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("An error occurred during user updating ");
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        logger.info("New request: user with id " +id+ " updated in db");
        return "redirect:/user/list";
    }

    /**
     * This method allows you to delete a user
     *
     * @param id the id of the user to delete
     * @return the userList template
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        logger.info("New request: user with id " +id+ " deleted from db");
        return "redirect:/user/list";
    }
}
